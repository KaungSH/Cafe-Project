package cafe.project.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

//	public Orders findById(String id) {
//		Orders entity = ordersRepository.findById(id);
//		if (entity == null) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order");
//		}
//		return entity;
//	}
	
	public Orders findById(String id) {
	    try {
	        Orders entity = ordersRepository.findById(id);

	        if (entity == null) {
	            throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Order not found"
	            );
	        }

	        return entity;

	    } catch (EmptyResultDataAccessException e) {
	        throw new ResponseStatusException(
	            HttpStatus.NOT_FOUND, "Order not found"
	        );
	    }
	}
	
	public int save(Orders order) {
		if (order == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order data is required");
		}
		return ordersRepository.save(order);
	}

	public boolean isPaymentDone(String orderId) {
		return ordersRepository.isPaymentDone(orderId);
	}

	public int updateTotalAmount(String id, BigDecimal totalAmount) {
		if (totalAmount == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Total amount is required");
		}
		Orders entity = ordersRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order");
		}
		return ordersRepository.updateTotalAmoun(id, totalAmount);
	}

	public int setReceivedTime(String orderId) {
		Orders entity = ordersRepository.findById(orderId);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order");
		}
		return ordersRepository.setReceivedTime(orderId);
	}

	public List<Orders> findNotReceivedAll() {
		return ordersRepository.findNotReceivedAll();
	}

	public List<Orders> findReceivedAll() {
		return ordersRepository.findReceivedAll();
	}

	public int edit(String id, Orders order) {
		if (order == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order data is required");
		}
		Orders entity = ordersRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order");
		}
		return ordersRepository.edit(id, order);
	}

	public int delete(String id) {
		Orders entity = ordersRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order");
		}
		return ordersRepository.delete(id);
	}

	public int restore(String id) {
		Orders entity = ordersRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order");
		}
		return ordersRepository.restore(id);
	}

	public int permanentDelete(String id) {
		Orders entity = ordersRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order");
		}
		return ordersRepository.permanentDelete(id);
	}
}