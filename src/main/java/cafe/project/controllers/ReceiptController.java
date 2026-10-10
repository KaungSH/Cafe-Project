//package cafe.project.controllers;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import cafe.project.ReceiptPdfGenerator;
//import cafe.project.repositories.OrderDetailsRepository;
//import cafe.project.repositories.OrdersRepository;
//import cafe.project.repositories.entities.OrderDetails;
//import cafe.project.repositories.entities.Orders;
//import cafe.project.repositories.entities.Payment;
//import cafe.project.services.OrderDetailsService;
//import cafe.project.services.PaymentService;
//
//@Controller
//@RequestMapping("/receipt")
//public class ReceiptController {
//
//    private final OrdersRepository ordersRepository;
//    private final OrderDetailsRepository orderDetailsRepository;
//    private final PaymentService paymentService;
//    private final OrderDetailsService orderDetailsService;
//
//    public ReceiptController(
//            OrdersRepository ordersRepository,
//            OrderDetailsRepository orderDetailsRepository,
//            PaymentService paymentService,
//            OrderDetailsService orderDetailsService) {
//
//        this.ordersRepository = ordersRepository;
//        this.orderDetailsRepository = orderDetailsRepository;
//        this.paymentService = paymentService;
//        this.orderDetailsService = orderDetailsService;
//    }
//
//    @GetMapping("/{orderId}")
//    public ResponseEntity<byte[]> generateReceipt(@PathVariable String orderId) {
//        try {
//            // 1. Get Order
//            Orders order = ordersRepository.findById(orderId);
//            if (order == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//
//            // 2. Get Order Details
//            List<OrderDetails> details = orderDetailsRepository.findByOrderId(orderId);
//
//            // 3. Order data Map
//            Map<String, Object> orderData = new HashMap<>();
//            orderData.put("branch_name", order.getBranch_name());
//            orderData.put("branch_id", order.getBranch_id());
//            orderData.put("token_number", order.getToken_number());
//            orderData.put("employee_name", order.getEmployee_name());
//            orderData.put("employee_id", order.getEmployee_id());
//            orderData.put("created_time", order.getCreated_time());
//            orderData.put("received_time", order.getReceived_time());
//
//         // 4. Subtotal 
//            BigDecimal subtotal = orderDetailsService.calculateTotalAmount(orderId);
//            if (subtotal == null || subtotal.compareTo(BigDecimal.ZERO) == 0) {
//                subtotal = BigDecimal.ZERO;
//                if (details != null) {
//                    for (OrderDetails d : details) {
//                        int qty = (d.getQuantity() != null && d.getQuantity() > 0) ? d.getQuantity() : 1;
//                        BigDecimal price = d.getPrice() != null ? d.getPrice() : BigDecimal.ZERO;
//                        subtotal = subtotal.add(price.multiply(BigDecimal.valueOf(qty)));
//                    }
//                }
//            }
//
//            // 5. Payment Information နှင့် Discount ရှာဖွေခြင်း
//            Payment payment = paymentService.findByOrderId(orderId);
//            BigDecimal discountAmount = BigDecimal.ZERO;
//
//            if (payment != null) {
//                orderData.put("payment_method", payment.getPay_method_name() != null ? payment.getPay_method_name() : "Cash");
//
//                // Payment 
//                if (payment.getDiscount() != null && payment.getDiscount() > 0) {
//                    discountAmount = BigDecimal.valueOf(payment.getDiscount());
//                }
//            } else {
//                orderData.put("payment_method", "N/A");
//            }
//
//            // Payment
//            if (discountAmount.compareTo(BigDecimal.ZERO) == 0) {
//                BigDecimal serviceDiscount = orderDetailsService.calculateDiscountAmount(orderId);
//                if (serviceDiscount != null && serviceDiscount.compareTo(BigDecimal.ZERO) > 0) {
//                    discountAmount = serviceDiscount;
//                }
//            }
//
//            BigDecimal finalAmount = subtotal.subtract(discountAmount);
//            if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
//                finalAmount = BigDecimal.ZERO;
//            }
//
//            // ReceiptPdfGenerator 
//            orderData.put("subtotal", subtotal);
//            orderData.put("discount_amount", discountAmount);
//            orderData.put("final_amount", finalAmount);
//            orderData.put("total_amount", finalAmount);
//
//            // 6. Convert OrderDetails 
//            List<Map<String, Object>> items = new java.util.ArrayList<>();
//            if (details != null) {
//                for (OrderDetails detail : details) {
//                    Map<String, Object> item = new HashMap<>();
//                    item.put("product_name", detail.getProduct_name());
//                    item.put("size_name", detail.getProduct_name() != null ? detail.getProduct_name() : "");
//                    
//                    // Screenshot 
//                    int qty = (detail.getQuantity() != null && detail.getQuantity() > 0) ? detail.getQuantity() : 1;
//                    item.put("quantity", qty);
//                    item.put("price", detail.getPrice());
//                    item.put("remark", detail.getRemark());
//                    items.add(item);
//                }
//            }
//
//            // 7. Generate PDF
//            ReceiptPdfGenerator generator = new ReceiptPdfGenerator();
//            byte[] pdf = generator.generateReceiptPdfBytes(orderId, orderData, items);
//
//            // 8. Return PDF
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=receipt-" + orderId + ".pdf")
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(pdf);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//}
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

    @GetMapping("/{orderId}")
    public ResponseEntity<byte[]> generateReceipt(@PathVariable String orderId) {
        try {
            // 1. Get Order (Fallback to mock order data if not found in DB)
            Orders order = ordersRepository.findById(orderId);
            Map<String, Object> orderData = new HashMap<>();

            if (order != null) {
                orderData.put("branch_name", order.getBranch_name());
                orderData.put("branch_id", order.getBranch_id());
                orderData.put("token_number", order.getToken_number());
                orderData.put("employee_name", order.getEmployee_name());
                orderData.put("employee_id", order.getEmployee_id());
                orderData.put("created_time", order.getCreated_time());
                orderData.put("received_time", order.getReceived_time());
            } else {
                // Mock Order Header Data for Testing
                orderData.put("branch_name", "Downtown Cafe - Branch 1");
                orderData.put("branch_id", "BR-001");
                orderData.put("token_number", "A-108");
                orderData.put("employee_name", "Aung Aung");
                orderData.put("employee_id", "EMP-042");
                orderData.put("created_time", "2026-10-10 11:45:00");
                orderData.put("received_time", "2026-10-10 11:52:30");
            }

            // 2. Mock Items List for PDF
            List<Map<String, Object>> items = new ArrayList<>();

            Map<String, Object> item1 = new HashMap<>();
            item1.put("product_name", "Iced Caramel Macchiato");
            item1.put("size_name", "Large (16oz)");
            item1.put("quantity", 2);
            item1.put("price", new BigDecimal("5500.00"));
            item1.put("remark", "Less sweet, Oat milk");
            items.add(item1);

            Map<String, Object> item2 = new HashMap<>();
            item2.put("product_name", "Matcha Espresso Fusion");
            item2.put("size_name", "Regular (12oz)");
            item2.put("quantity", 1);
            item2.put("price", new BigDecimal("4800.00"));
            item2.put("remark", "Normal ice");
            items.add(item2);

            Map<String, Object> item3 = new HashMap<>();
            item3.put("product_name", "Almond Croissant");
            item3.put("size_name", "Standard");
            item3.put("quantity", 1);
            item3.put("price", new BigDecimal("3500.00"));
            item3.put("remark", "Warm up");
            items.add(item3);

            Map<String, Object> item4 = new HashMap<>();
            item4.put("product_name", "Americano");
            item4.put("size_name", "Hot / Regular");
            item4.put("quantity", 1);
            item4.put("price", new BigDecimal("3000.00"));
            item4.put("remark", "-");
            items.add(item4);

            // 3. Calculate Subtotal from Mock Items
            BigDecimal subtotal = BigDecimal.ZERO;
            for (Map<String, Object> item : items) {
                int qty = (int) item.get("quantity");
                BigDecimal price = (BigDecimal) item.get("price");
                subtotal = subtotal.add(price.multiply(BigDecimal.valueOf(qty)));
            }

            // 4. Payment Info & Discounts
            Payment payment = paymentService != null ? paymentService.findByOrderId(orderId) : null;
            BigDecimal discountAmount = BigDecimal.ZERO;

            if (payment != null) {
                orderData.put("payment_method", payment.getPay_method_name() != null ? payment.getPay_method_name() : "Cash");
                if (payment.getDiscount() != null && payment.getDiscount() > 0) {
                    discountAmount = BigDecimal.valueOf(payment.getDiscount());
                }
            } else {
                orderData.put("payment_method", "Cash / KBZPay");
                discountAmount = new BigDecimal("1000.00"); // Mock discount for testing
            }

            BigDecimal finalAmount = subtotal.subtract(discountAmount);
            if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
                finalAmount = BigDecimal.ZERO;
            }

            // Summary data passed to the generator
            orderData.put("subtotal", subtotal);
            orderData.put("discount_amount", discountAmount);
            orderData.put("final_amount", finalAmount);
            orderData.put("total_amount", finalAmount);

            // 5. Generate PDF
            ReceiptPdfGenerator generator = new ReceiptPdfGenerator();
            byte[] pdf = generator.generateReceiptPdfBytes(orderId, orderData, items);

            // 6. Return PDF Stream
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=receipt-" + orderId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}