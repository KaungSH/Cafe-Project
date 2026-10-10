package cafe.project.controllers;

import java.math.BigDecimal;
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
            // 1. Get Order
            Orders order = ordersRepository.findById(orderId);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // 2. Get Order Details
            List<OrderDetails> details = orderDetailsRepository.findByOrderId(orderId);

            // 3. Order data Map
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("branch_name", order.getBranch_name());
            orderData.put("branch_id", order.getBranch_id());
            orderData.put("token_number", order.getToken_number());
            orderData.put("employee_name", order.getEmployee_name());
            orderData.put("employee_id", order.getEmployee_id());
            orderData.put("created_time", order.getCreated_time());
            orderData.put("received_time", order.getReceived_time());

         // 4. Subtotal တွက်ချက်ခြင်း
            BigDecimal subtotal = orderDetailsService.calculateTotalAmount(orderId);
            if (subtotal == null || subtotal.compareTo(BigDecimal.ZERO) == 0) {
                subtotal = BigDecimal.ZERO;
                if (details != null) {
                    for (OrderDetails d : details) {
                        int qty = (d.getQuantity() != null && d.getQuantity() > 0) ? d.getQuantity() : 1;
                        BigDecimal price = d.getPrice() != null ? d.getPrice() : BigDecimal.ZERO;
                        subtotal = subtotal.add(price.multiply(BigDecimal.valueOf(qty)));
                    }
                }
            }

            // 5. Payment Information နှင့် Discount ရှာဖွေခြင်း
            Payment payment = paymentService.findById(orderId);
            BigDecimal discountAmount = BigDecimal.ZERO;

            if (payment != null) {
                orderData.put("payment_method", payment.getPay_method_name() != null ? payment.getPay_method_name() : "Cash");

                // Payment ရိုက်ချိန်က ထည့်ခဲ့သော discount ကို ယူခြင်း
                if (payment.getDiscount() != null && payment.getDiscount() > 0) {
                    discountAmount = BigDecimal.valueOf(payment.getDiscount());
                }
            } else {
                orderData.put("payment_method", "N/A");
            }

            // Payment တွင် discount မတွေ့ပါက orderDetailsService မှ ဆက်လက်စစ်ဆေးခြင်း
            if (discountAmount.compareTo(BigDecimal.ZERO) == 0) {
                BigDecimal serviceDiscount = orderDetailsService.calculateDiscountAmount(orderId);
                if (serviceDiscount != null && serviceDiscount.compareTo(BigDecimal.ZERO) > 0) {
                    discountAmount = serviceDiscount;
                }
            }

            BigDecimal finalAmount = subtotal.subtract(discountAmount);
            if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
                finalAmount = BigDecimal.ZERO;
            }

            // ReceiptPdfGenerator ထဲတွင် ဖတ်မည့် key များ
            orderData.put("subtotal", subtotal);
//            orderData.put("discount_amount", discountAmount);
            orderData.put("discount_amount", new BigDecimal("1000"));
            orderData.put("final_amount", finalAmount);
            orderData.put("total_amount", finalAmount);
            

            // 6. Convert OrderDetails (Qty 0 မဖြစ်စေရန် စစ်ပေးထားပါသည်)
            List<Map<String, Object>> items = new java.util.ArrayList<>();
            if (details != null) {
                for (OrderDetails detail : details) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("product_name", detail.getProduct_name());
                    item.put("size_name", detail.getProduct_name() != null ? detail.getProduct_name() : "");
                    
                    // Screenshot အရ Qty 0 ဖြစ်နေမှုကို ကာကွယ်ရန်
                    int qty = (detail.getQuantity() != null && detail.getQuantity() > 0) ? detail.getQuantity() : 1;
                    item.put("quantity", qty);
                    item.put("price", detail.getPrice());
                    item.put("remark", detail.getRemark());
                    items.add(item);
                }
            }

            // 7. Generate PDF
            ReceiptPdfGenerator generator = new ReceiptPdfGenerator();
            byte[] pdf = generator.generateReceiptPdfBytes(orderId, orderData, items);

            // 8. Return PDF
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
//package cafe.project.controllers;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
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
//            // =========================================================
//            // ၁။ FAKE ORDER DATA (ဆိုင်အမည်၊ Token၊ Cashier အချက်အလက်)
//            // =========================================================
//            Map<String, Object> orderData = new HashMap<>();
//            orderData.put("branch_name", "Cafe Luft");
//            orderData.put("token_number", "88");
//            orderData.put("employee_name", "admin (ADMIN)");
//            orderData.put("employee_id", "EMP-001");
//            orderData.put("created_time", "2026-10-09 15:30:00");
//            orderData.put("payment_method", "Cash");
//
//            // =========================================================
//            // ၂။ FAKE ITEMS (Loop သုံး၍ ပစ္စည်း ၃၀ အထိ အလိုအလျောက် ထည့်ခြင်း)
//            // =========================================================
//            List<Map<String, Object>> items = new ArrayList<>();
//            BigDecimal subtotal = BigDecimal.ZERO;
//
//            String[] menuNames = {
//                "Cappuccino", "Cafe Latte", "Americano", "Matcha Green Tea",
//                "Croissant", "Cheesecake", "Caramel Macchiato", "Espresso",
//                "Mocha Frappe", "Lemon Tea", "Blueberry Muffin", "Tiramisu Cake",
//                "Milk Tea", "Cold Brew", "Garlic Bread"
//            };
//
//            String[] sizeNames = {"Regular", "Large", "Medium", "Slice", "Piece"};
//
//            // ပစ္စည်း အခု ၃၀ အထိ ဖန်တီးခြင်း
//            for (int i = 1; i <= 30; i++) {
//                Map<String, Object> item = new HashMap<>();
//                
//                String pName = menuNames[(i - 1) % menuNames.length] + " #" + i;
//                String sName = sizeNames[(i - 1) % sizeNames.length];
//                int qty = (i % 3 == 0) ? 2 : 1;
//                BigDecimal price = new BigDecimal(3000 + ((i % 5) * 500)); // 3000 မှ 5000 ကြား
//
//                item.put("product_name", pName);
//                item.put("size_name", sName);
//                item.put("quantity", qty);
//                item.put("price", price);
//
//                // စာကြောင်း ခေါက်ချိုးဆင်းစေရန် အချို့ကို remark ရှည်ရှည် ထည့်ခြင်း
//                if (i % 2 == 0) {
//                    item.put("remark", "Less Sweet, Extra Ice");
//                } else if (i % 5 == 0) {
//                    item.put("remark", "Take away box");
//                } else {
//                    item.put("remark", "");
//                }
//
//                items.add(item);
//
//                // Subtotal ပေါင်းစပ်ခြင်း
//                subtotal = subtotal.add(price.multiply(BigDecimal.valueOf(qty)));
//            }
//
//            // =========================================================
//            // ၃။ FAKE SUBTOTAL & DISCOUNT CALCULATION
//            // =========================================================
//            BigDecimal discountAmount = new BigDecimal("5000"); // 5,000 MMK Discount အတု
//            BigDecimal finalAmount = subtotal.subtract(discountAmount);
//
//            orderData.put("subtotal", subtotal);
//            orderData.put("discount_amount", discountAmount);
//            orderData.put("final_amount", finalAmount);
//            orderData.put("total_amount", finalAmount);
//
//            // =========================================================
//            // ၄။ GENERATE PDF & RETURN
//            // =========================================================
//            ReceiptPdfGenerator generator = new ReceiptPdfGenerator();
//            byte[] pdf = generator.generateReceiptPdfBytes(orderId, orderData, items);
//
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