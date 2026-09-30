package cafe.project.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.models.OrderDetailsDto;
import cafe.project.repositories.OrderDetailsRepository;
import cafe.project.repositories.entities.OrderDetails;

@Service
public class OrderDetailsService {

	private final OrderDetailsRepository orderDetailsRepository;

	public OrderDetailsService(OrderDetailsRepository orderDetailsRepository) {
		this.orderDetailsRepository = orderDetailsRepository;
	}

	// Get all order details
	public List<OrderDetails> findAll() {
		return orderDetailsRepository.findAll();
	}

	// Get order detail by ID
	public OrderDetails findById(String id) {
		return orderDetailsRepository.findById(id);
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

		for (OrderDetails orderDetail : dto.getOrderDetails()) {

			orderDetailsRepository.save(orderDetail);
		}
	}

	// Update order detail
	public void edit(OrderDetailsDto dto) {

		for (OrderDetails orderDetail : dto.getOrderDetails()) {

			orderDetailsRepository.edit(orderDetail.getOrder_detail_id(), orderDetail);
		}
	}

	// Delete order detail
	public void delete(OrderDetailsDto dto) {

		for (OrderDetails orderDetail : dto.getOrderDetails()) {

			orderDetailsRepository.delete(orderDetail.getOrder_detail_id());
		}
	}
}
