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

	public List<Orders> findAll() {
		return ordersRepository.findAll();
	}

	public List<Orders> findDeletedAll() {
		return ordersRepository.findDeletedAll();
	}

	public Orders findById(String id) {
		return ordersRepository.findById(id);
	}

	public int save(Orders order) {
		return ordersRepository.save(order);
	}

	public int edit(String id, Orders order) {
		return ordersRepository.edit(id, order);
	}

	public int delete(String id) {
		return ordersRepository.delete(id);
	}

	public int restore(String id) {
		return ordersRepository.restore(id);
	}

	public int permanentDelete(String id) {
		return ordersRepository.permanentDelete(id);
	}
}