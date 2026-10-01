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
	public void edit(OrderDetailsDto dto, String orderId) {

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

		for (OrderDetails orderDetail : dto.getOrderDetails()) {

			orderDetailsRepository.delete(orderDetail.getOrder_detail_id());
		}
	}
}
