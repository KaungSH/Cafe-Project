
package cafe.project.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.ReceiptPdfGenerator;
import cafe.project.repositories.OrderDetailsRepository;
import cafe.project.repositories.OrdersRepository;
import cafe.project.repositories.entities.OrderDetails;
import cafe.project.repositories.entities.Orders;
import cafe.project.repositories.entities.Payment;
import cafe.project.services.OrderDetailsService;
import cafe.project.services.PaymentService;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {

    private final OrdersRepository ordersRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final PaymentService paymentService;
    private final OrderDetailsService orderDetailsService;

    public ReceiptController(
            OrdersRepository ordersRepository,
            OrderDetailsRepository orderDetailsRepository,
            PaymentService paymentService,
            OrderDetailsService orderDetailsService) {

        this.ordersRepository = ordersRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.paymentService = paymentService;
        this.orderDetailsService = orderDetailsService;
    }

    // =========================================
    // 1. RECEIPT PREVIEW PAGE
    // URL: /receipt/preview/{orderId}
    // =========================================

    @GetMapping("/preview/{orderId}")
    public String previewReceipt(
            @PathVariable String orderId,
            Model model) {

        Orders order = ordersRepository.findById(orderId);

        if (order == null) {
            return "redirect:/order-with-details";
        }

        List<OrderDetails> details =
                orderDetailsRepository.findByOrderId(orderId);

        Payment payment =
                paymentService.findByOrderId(orderId);

        BigDecimal subtotal =
                orderDetailsService.calculateTotalAmount(orderId);

        if (subtotal == null) {
            subtotal = BigDecimal.ZERO;
        }

        BigDecimal discountAmount = BigDecimal.ZERO;

        if (payment != null
                && payment.getDiscount() != null
                && payment.getDiscount() > 0) {

            discountAmount =
                    BigDecimal.valueOf(payment.getDiscount());
        }

        // Fallback discount calculation
        if (discountAmount.compareTo(BigDecimal.ZERO) == 0) {

            BigDecimal serviceDiscount =
                    orderDetailsService.calculateDiscountAmount(orderId);

            if (serviceDiscount != null
                    && serviceDiscount.compareTo(BigDecimal.ZERO) > 0) {

                discountAmount = serviceDiscount;
            }
        }

        BigDecimal total = subtotal
                .subtract(discountAmount)
                .max(BigDecimal.ZERO);

        // Send data to Thymeleaf
        model.addAttribute("order", order);
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderDetails", details);
        model.addAttribute("payment", payment);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("total", total);

        return "payments/receipt-preview";
    }

    // =========================================
    // 2. DOWNLOAD RECEIPT AS PDF
    // URL: /receipt/{orderId}
    // =========================================

    @GetMapping("/{orderId}")
    public ResponseEntity<byte[]> generateReceipt(
            @PathVariable String orderId) {

        try {

            Orders order = ordersRepository.findById(orderId);

            if (order == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }

            List<OrderDetails> details =
                    orderDetailsRepository.findByOrderId(orderId);

            // Prepare order information
            Map<String, Object> orderData = new HashMap<>();

            orderData.put("order_id", orderId);
            orderData.put("branch_name", order.getBranch_name());
            orderData.put("branch_id", order.getBranch_id());
            orderData.put("token_number", order.getToken_number());
            orderData.put("employee_name", order.getEmployee_name());
            orderData.put("employee_id", order.getEmployee_id());
            orderData.put("created_time", order.getCreated_time());
            orderData.put("received_time", order.getReceived_time());

            // Calculate subtotal
            BigDecimal subtotal =
                    orderDetailsService.calculateTotalAmount(orderId);

            if (subtotal == null
                    || subtotal.compareTo(BigDecimal.ZERO) == 0) {

                subtotal = BigDecimal.ZERO;

                if (details != null) {
                    for (OrderDetails detail : details) {

                        int quantity =
                                detail.getQuantity() != null
                                && detail.getQuantity() > 0
                                ? detail.getQuantity() : 1;

                        BigDecimal price =
                                detail.getPrice() != null
                                ? detail.getPrice()
                                : BigDecimal.ZERO;

                        subtotal = subtotal.add(
                                price.multiply(
                                        BigDecimal.valueOf(quantity)
                                )
                        );
                    }
                }
            }

            // Payment information
            Payment payment =
                    paymentService.findByOrderId(orderId);

            BigDecimal discountAmount = BigDecimal.ZERO;

            if (payment != null) {

                String paymentMethod =
                        payment.getPay_method_name() != null
                        ? payment.getPay_method_name()
                        : "Cash";

                orderData.put("payment_method", paymentMethod);

                if (payment.getDiscount() != null
                        && payment.getDiscount() > 0) {

                    discountAmount =
                            BigDecimal.valueOf(payment.getDiscount());
                }

            } else {
                orderData.put("payment_method", "N/A");
            }

            // Fallback discount calculation
            if (discountAmount.compareTo(BigDecimal.ZERO) == 0) {

                BigDecimal serviceDiscount =
                        orderDetailsService.calculateDiscountAmount(orderId);

                if (serviceDiscount != null
                        && serviceDiscount.compareTo(BigDecimal.ZERO) > 0) {

                    discountAmount = serviceDiscount;
                }
            }

            // Calculate final amount
            BigDecimal finalAmount = subtotal
                    .subtract(discountAmount)
                    .max(BigDecimal.ZERO);

            orderData.put("subtotal", subtotal);
            orderData.put("discount_amount", discountAmount);
            orderData.put("final_amount", finalAmount);
            orderData.put("total_amount", finalAmount);

            // Prepare PDF items
            List<Map<String, Object>> items = new ArrayList<>();

            if (details != null) {
                for (OrderDetails detail : details) {

                    Map<String, Object> item = new HashMap<>();

                    item.put("product_name", detail.getProduct_name());

                    // Retain the existing entity fields
                    item.put(
                            "size_name",
                            detail.getProduct_name() != null
                                    ? detail.getProduct_name()
                                    : ""
                    );

                    int quantity =
                            detail.getQuantity() != null
                            && detail.getQuantity() > 0
                            ? detail.getQuantity() : 1;

                    item.put("quantity", quantity);

                    item.put(
                            "price",
                            detail.getPrice() != null
                                    ? detail.getPrice()
                                    : BigDecimal.ZERO
                    );

                    item.put("remark", detail.getRemark());

                    items.add(item);
                }
            }

            // Generate PDF bytes
            ReceiptPdfGenerator generator =
                    new ReceiptPdfGenerator();

            byte[] pdf = generator.generateReceiptPdfBytes(
                    orderId,
                    orderData,
                    items
            );

            if (pdf == null || pdf.length == 0) {
                return ResponseEntity
                        .internalServerError()
                        .build();
            }

            // Return downloadable PDF
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"receipt-"
                                    + orderId + ".pdf\""
                    )
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdf.length)
                    .body(pdf);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
