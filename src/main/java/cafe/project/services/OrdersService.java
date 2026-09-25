package cafe.project.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.repositories.OrdersRepository;
import cafe.project.repositories.entities.Orders;

@Service
public class OrdersService {

	private final OrdersRepository ordersRepository;

	public OrdersService(OrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}

	// Get all orders
	public List<Orders> findAll() {
		return ordersRepository.findAll();
	}

	// Get deleted orders
	public List<Orders> findDeletedAll() {
		return ordersRepository.findDeletedAll();
	}

	// Get order by ID
	public Orders findById(String id) {
		return ordersRepository.findById(id);
	}

	// Add order
	public int save(Orders order) {
		return ordersRepository.save(order);
	}

	// Update order
	public int edit(String id, Orders order) {
		return ordersRepository.edit(id, order);
	}

	// Delete order
	public int delete(String id) {
		return ordersRepository.delete(id);
	}
}