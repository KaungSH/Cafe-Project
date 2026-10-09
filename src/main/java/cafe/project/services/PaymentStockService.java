
package cafe.project.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		paymentService.save(payment);

		orderDetailsService.reduceStock(dto, branchId);
	}
}
