package cafe.project.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.repositories.entities.OrderDetails;
import cafe.project.repositories.entities.Orders;
import cafe.project.repositories.entities.Payment;
import cafe.project.services.OrderDetailsService;
import cafe.project.services.OrdersService;
import cafe.project.services.PayMethodService;
import cafe.project.services.PaymentService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/payments")
public class PaymentController {

	private final PaymentService paymentService;
	private final OrdersService ordersService;
	private final OrderDetailsService orderDetailsService;
	private final PayMethodService payMethodService;

	public PaymentController(PaymentService paymentService, OrdersService orderService,
			OrderDetailsService orderDetailsService, PayMethodService payMethodService) {
		this.paymentService = paymentService;
		this.ordersService = orderService;
		this.orderDetailsService = orderDetailsService;
		this.payMethodService = payMethodService;
	}

	@GetMapping
	public String index(Model model) {

		model.addAttribute("payments", paymentService.findAll());

		return "payments/index";
	}

	@GetMapping("/add")
	public String add(@RequestParam("order_id") String order_id, Model model) {

		Orders order = ordersService.findById(order_id);

		List<OrderDetails> orderDetails = orderDetailsService.findByOrderId(order_id);

		BigDecimal subtotal = orderDetailsService.calculateTotalAmount(order_id);

		BigDecimal discountAmount = orderDetailsService.calculateDiscountAmount(order_id);

		BigDecimal finalAmount = subtotal.subtract(discountAmount);

		boolean hasDiscount = discountAmount.compareTo(BigDecimal.ZERO) > 0;

		model.addAttribute("order", order);

		model.addAttribute("orderDetails", orderDetails);

		model.addAttribute("payment", new Payment());

		model.addAttribute("payMethods", payMethodService.getAllActivePayMethods());

		model.addAttribute("subtotal", subtotal);

		model.addAttribute("discountAmount", discountAmount);

		model.addAttribute("finalAmount", finalAmount);

		model.addAttribute("hasDiscount", hasDiscount);

		return "payments/add";
		
	}

	@PostMapping("/add")
	public String save(@ModelAttribute("payment") Payment payment, @RequestParam("order_id") String orderId,
			HttpSession session) {

		LoginDto user = (LoginDto) session.getAttribute("loggedInUser");

		payment.setOrder_id(orderId);
		payment.setPaid_time(LocalDateTime.now());
		payment.setDate(LocalDate.now());
		payment.setFilepath("");

		if (user != null) {
			payment.setEmployee_id(user.getEmployee_id());
		}

		paymentService.save(payment);

		return "redirect:/order-with-details";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {

		Payment existingPayment = paymentService.findById(id);

		if (existingPayment != null) {

			model.addAttribute("payment", existingPayment);

			return "payments/edit";
		}

		return "redirect:/payments";
	}


	@PostMapping("/edit/{id}")
	public String update(@PathVariable String id, @ModelAttribute("payment") Payment payment) {

		paymentService.edit(id, payment);

		return "redirect:/payments";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable String id) {

		paymentService.delete(id);

		return "redirect:/payments";
	}

	@GetMapping("/deleted")
	public String deletedList(Model model) {
		model.addAttribute("payments", paymentService.findDeletedAll());
		return "/payments/deletedList";
	}

	@GetMapping("/restore/{id}")
	public String restore(@PathVariable("id") String id) {
		paymentService.restore(id);
		return "redirect:/payments/deleted";
	}

	@GetMapping("/real-deleted/{id}")
	public String hardDelete(@PathVariable("id") String id) {
		paymentService.hardDelete(id);
		return "redirect:/payments/deleted";
	}
}