package cafe.project.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.OrderDetailsDto;
import cafe.project.models.ProductEntryModel;
import cafe.project.repositories.OrderDetailsRepository;
import cafe.project.repositories.entities.OrderDetails;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final IngredientBatchService ingredientBatchService;
    private final ProductService productService;

    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository,
            IngredientBatchService ingredientBatchService, ProductService productService) {

        this.orderDetailsRepository = orderDetailsRepository;
        this.ingredientBatchService = ingredientBatchService;
        this.productService = productService;
    }

    // Get all order details
    public List<OrderDetails> findAll() {
        return orderDetailsRepository.findAll();
    }

    // Get order detail by ID
    public OrderDetails findById(String id) {
        OrderDetails entity = orderDetailsRepository.findById(id);
        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Detail");
        }
        return entity;
    }

    // Get details by Order ID
    public List<OrderDetails> findByOrderId(String orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }

    // Get Order IDs
    public List<String> findOrderIds() {
        return orderDetailsRepository.findOrderIds();
    }

    // Add order detail
    public void save(OrderDetailsDto dto) {
        if (dto == null || dto.getOrderDetails() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Details data is required");
        }

        for (OrderDetails orderDetail : dto.getOrderDetails()) {
            orderDetailsRepository.save(orderDetail);
        }
    }

    public BigDecimal calculateTotalAmount(String orderId) {
        return orderDetailsRepository.calculateTotalAmount(orderId);
    }

    public BigDecimal calculateDiscountAmount(String orderId) {
        return orderDetailsRepository.calculateDiscountAmount(orderId);
    }

    // Update order detail
    public void edit(OrderDetailsDto dto, String orderId) {
        if (dto == null || dto.getOrderDetails() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Details data is required");
        }

        // Get existing details from database
        List<OrderDetails> oldDetails = orderDetailsRepository.findByOrderId(orderId);

        // Update or Insert
        for (OrderDetails detail : dto.getOrderDetails()) {

            detail.setOrder_id(orderId);

            if (detail.getOrder_detail_id() == null || detail.getOrder_detail_id().isEmpty()) {

                // NEW DETAIL
                orderDetailsRepository.save(detail);

            } else {

                // EXISTING DETAIL
                orderDetailsRepository.edit(detail.getOrder_detail_id(), detail);
            }
        }

        // Delete removed details
        for (OrderDetails oldDetail : oldDetails) {

            boolean found = false;

            for (OrderDetails detail : dto.getOrderDetails()) {

                if (oldDetail.getOrder_detail_id().equals(detail.getOrder_detail_id())) {

                    found = true;
                    break;
                }
            }

            if (!found) {

                orderDetailsRepository.delete(oldDetail.getOrder_detail_id());

            }
        }
    }

    // Delete order detail
    public void delete(OrderDetailsDto dto) {
        if (dto == null || dto.getOrderDetails() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Details data is required");
        }

        for (OrderDetails orderDetail : dto.getOrderDetails()) {
            orderDetailsRepository.delete(orderDetail.getOrder_detail_id());
        }
    }

    public void checkStock(OrderDetailsDto dto, String branchId) {
        if (dto == null || dto.getOrderDetails() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Details data is required");
        }

        for (OrderDetails orderDetail : dto.getOrderDetails()) {

            ProductEntryModel product = productService.findById(orderDetail.getProduct_id());

            if (product == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product");
            }

            List<String> ingredientIds = product.getIngredient_ids();

            List<Double> quantities = product.getQuantity_required();

            int orderQuantity = orderDetail.getQuantity();

            for (int i = 0; i < ingredientIds.size(); i++) {

                String ingredientId = ingredientIds.get(i);

                BigDecimal requiredPerProduct = BigDecimal.valueOf(quantities.get(i));

                BigDecimal requiredQuantity = requiredPerProduct.multiply(BigDecimal.valueOf(orderQuantity));

                BigDecimal availableQuantity = ingredientBatchService.getAvailableQuantity(ingredientId, branchId);

                if (availableQuantity.compareTo(requiredQuantity) < 0) {

                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                        "Not enough ingredient stock. Required: " + requiredQuantity + ", Available: " + availableQuantity);
                }
            }
        }
    }

    public void reduceStock(OrderDetailsDto dto, String branchId) {
        if (dto == null || dto.getOrderDetails() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Details data is required");
        }

        for (OrderDetails orderDetail : dto.getOrderDetails()) {

            ProductEntryModel product = productService.findById(orderDetail.getProduct_id());

            List<String> ingredientIds = product.getIngredient_ids();

            List<Double> quantities = product.getQuantity_required();

            int orderQuantity = orderDetail.getQuantity();

            for (int i = 0; i < ingredientIds.size(); i++) {

                String ingredientId = ingredientIds.get(i);

                BigDecimal requiredPerProduct = BigDecimal.valueOf(quantities.get(i));

                BigDecimal requiredQuantity = requiredPerProduct.multiply(BigDecimal.valueOf(orderQuantity));

                ingredientBatchService.reduceStockFIFO(ingredientId, branchId, requiredQuantity);

            }
        }
    }
}