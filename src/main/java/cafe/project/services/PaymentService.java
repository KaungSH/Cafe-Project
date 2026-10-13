package cafe.project.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.repositories.PaymentRepository;
import cafe.project.repositories.entities.Payment;

@Service
public class PaymentService {

	private final PaymentRepository paymentRepository;

	public PaymentService(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}
	
	
	// GET PAYMENT BY ORDER ID
	public Payment findByOrderId(String orderId) {
	    return paymentRepository.findByOrderId(orderId);
	}
	

	// GET ALL
	public List<Payment> findAll(String branch_id) {
		return paymentRepository.findAll(branch_id);
	}
	
	// GET ALL
		public List<Payment> findAllAdmin() {
			return paymentRepository.findAllAdmin();
		}

	// GET DELETED
	public List<Payment> findDeletedAll(String branch_id) {
		return paymentRepository.findDeletedAll(branch_id);
	}

	// GET BY ID
	public Payment findById(String id) {
		Payment entity = paymentRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment");
		}
		return entity;
	}

	public boolean existsByOrderId(String orderId) {
		return paymentRepository.existsByOrderId(orderId);
	}

	// SAVE
	public int save(Payment payment) {
		if (payment == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment data is required");
		}
		return paymentRepository.save(payment);
	}

	// UPDATE
	public int edit(String id, Payment payment) {
		if (payment == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment data is required");
		}
		Payment entity = paymentRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment");
		}
		return paymentRepository.edit(id, payment);
	}

	// DELETE
	public int delete(String id) {
		Payment entity = paymentRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment");
		}
		return paymentRepository.delete(id);
	}

	public int restore(String id) {
		Payment entity = paymentRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment");
		}
		return paymentRepository.restore(id);
	}

	public int hardDelete(String id) {
		Payment entity = paymentRepository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment");
		}
		return paymentRepository.hardDelete(id);
	}
}