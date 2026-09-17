package cafe.project.HeinMinHtet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.HeinMinHtet.repositories.OrderDetailsRepository;
import cafe.project.HeinMinHtet.repositories.entities.OrderDetails;

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

	// Add order detail
	public int save(OrderDetails orderDetail) {
		return orderDetailsRepository.save(orderDetail);
	}

	// Update order detail
	public int edit(String id, OrderDetails orderDetail) {
		return orderDetailsRepository.edit(id, orderDetail);
	}

	// Delete order detail
	public int delete(String id) {
		return orderDetailsRepository.delete(id);
	}
}
