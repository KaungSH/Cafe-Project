package cafe.project.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.OrderDetailsDto;
import cafe.project.repositories.entities.Payment;

@Service
public class PaymentStockService {

	private final PaymentService paymentService;
	private final OrderDetailsService orderDetailsService;

	public PaymentStockService(PaymentService paymentService, OrderDetailsService orderDetailsService) {
		this.paymentService = paymentService;
		this.orderDetailsService = orderDetailsService;
	}

	@Transactional
	public void completePayment(Payment payment, OrderDetailsDto dto, String branchId) {
		if (payment == null || dto == null || branchId == null || branchId.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment and Order Details data are required");
		}

		paymentService.save(payment);

		orderDetailsService.reduceStock(dto, branchId);
	}
}