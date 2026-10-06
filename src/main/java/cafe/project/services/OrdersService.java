package cafe.project.services;

import java.math.BigDecimal;
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

	public List<Orders> findDeletedAll() {
		return ordersRepository.findDeletedAll();
	}

	public Orders findById(String id) {
		return ordersRepository.findById(id);
	}

	public int save(Orders order) {
		return ordersRepository.save(order);
	}

	public boolean isPaymentDone(String orderId) {
		return ordersRepository.isPaymentDone(orderId);
	}

	public int updateTotalAmount(String id, BigDecimal totalAmount) {
		return ordersRepository.updateTotalAmoun(id, totalAmount);
	}

	public int setReceivedTime(String orderId) {

		return ordersRepository.setReceivedTime(orderId);
	}

	public List<Orders> findNotReceivedAll() {

		return ordersRepository.findNotReceivedAll();

	}

	public List<Orders> findReceivedAll() {

		return ordersRepository.findReceivedAll();

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