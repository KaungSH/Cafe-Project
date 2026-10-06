package cafe.project.controllers;

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

@Controller
@RequestMapping("/receipt")
public class ReceiptController {

    private final OrdersRepository ordersRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public ReceiptController(
            OrdersRepository ordersRepository,
            OrderDetailsRepository orderDetailsRepository) {
        this.ordersRepository = ordersRepository;
        this.orderDetailsRepository = orderDetailsRepository;
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

            // 3. Order data
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("branch_name", order.getBranch_name());
            orderData.put("branch_id", order.getBranch_id());
            orderData.put("token_number", order.getToken_number());
            orderData.put("employee_name", order.getEmployee_name());
            orderData.put("employee_id", order.getEmployee_id());
            orderData.put("created_time", order.getCreated_time());
            orderData.put("received_time", order.getReceived_time());
            orderData.put("total_amount", order.getTotal_amount());
            orderData.put("payment_method", "Cash");

            // 4. Convert OrderDetails
            List<Map<String, Object>> items = new java.util.ArrayList<>();
            if (details != null) {
                for (OrderDetails detail : details) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("product_name", detail.getProduct_name());
                    item.put("quantity", detail.getQuantity());
                    item.put("price", detail.getPrice());
                    item.put("remark", detail.getRemark());
                    items.add(item);
                }
            }

            // 5. Generate PDF (Directly as byte[])
            ReceiptPdfGenerator generator = new ReceiptPdfGenerator();
            byte[] pdf = generator.generateReceiptPdfBytes(orderId, orderData, items);

            // 6. Return PDF
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