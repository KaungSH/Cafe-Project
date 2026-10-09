package cafe.project.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.OrderDetailsDto;
import cafe.project.repositories.entities.OrderDetails;
import cafe.project.repositories.entities.Orders;
import cafe.project.services.BranchService;
import cafe.project.services.EmployeeService;
import cafe.project.services.OrderDetailsService;
import cafe.project.services.OrdersService;
import cafe.project.services.PaymentService;
import cafe.project.services.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order-with-details")
public class OrderDetailsController {

	private final OrderDetailsService orderDetailsService;
	private final ProductService productService;
	private final OrdersService ordersService;
	private final BranchService branchService;
	private final EmployeeService employeeService;
	private final PaymentService paymentService;

	public OrderDetailsController(OrderDetailsService orderDetailsService, ProductService productService,
			OrdersService ordersService, BranchService branchService, EmployeeService employeeService,
			PaymentService paymentService) {

		this.orderDetailsService = orderDetailsService;
		this.productService = productService;
		this.ordersService = ordersService;
		this.branchService = branchService;
		this.employeeService = employeeService;
		this.paymentService = paymentService;
	}

	@GetMapping
	public String list(Model model, HttpSession session) {

		List<Orders> orders = ordersService.findNotReceivedAll();

		for (Orders order : orders) {

			boolean paymentDone = ordersService.isPaymentDone(order.getOrder_id());

			order.setPaymentDone(paymentDone);

			BigDecimal subtotal = orderDetailsService.calculateTotalAmount(order.getOrder_id());

			BigDecimal finalAmount = order.getTotal_amount();

			BigDecimal discountAmount = subtotal.subtract(finalAmount);

			order.setSubtotal(subtotal);
			order.setFinalAmount(finalAmount);
			order.setDiscountAmount(discountAmount);

			boolean hasDiscount = discountAmount.compareTo(BigDecimal.ZERO) > 0;

			order.setHasDiscount(hasDiscount);
		}

		model.addAttribute("orders", orders);
		model.addAttribute("orderDetails", orderDetailsService.findAll());

		return "orderwithdetails/list";
	}

	@GetMapping("/received-list")
	public String receivedList(Model model) {

		List<Orders> orders = ordersService.findReceivedAll();

		model.addAttribute("orders", orders);
		model.addAttribute("orderDetails", orderDetailsService.findAll());

		for (Orders order : orders) {

			boolean paid = ordersService.isPaymentDone(order.getOrder_id());

			order.setPaymentDone(paid);
		}

		return "orderwithdetails/receivedlist";
	}

	@GetMapping("/add")
	public String add(Model model, HttpSession session) {

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		Orders order = new Orders();
		OrderDetailsDto orderDetailsDto;

		List<OrderDetails> selectedDetails = (List<OrderDetails>) session.getAttribute("selectedOrderDetails");

		if (selectedDetails != null && !selectedDetails.isEmpty()) {
			orderDetailsDto = new OrderDetailsDto(selectedDetails);
		} else {
			orderDetailsDto = new OrderDetailsDto();
		}

		model.addAttribute("order", order);
		model.addAttribute("orderDetailsDto", orderDetailsDto);
		model.addAttribute("products", productService.findAllForOrder(ldto.getBranch_id()));

		return "orderwithdetails/add";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("order") Orders order, @ModelAttribute("orderDetailsDto") OrderDetailsDto dto,
			Model model, HttpSession session) {

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		order.setEmployee_id(ldto.getEmployee_id());
		order.setBranch_id(ldto.getBranch_id());
		order.setReceived_time(null);

		try {

			orderDetailsService.checkStock(dto, order.getBranch_id());

			ordersService.save(order);

			for (OrderDetails detail : dto.getOrderDetails()) {

				if (detail.getProduct_id() == null || detail.getProduct_id().trim().isEmpty()) {

					model.addAttribute("error", "Please select a product.");

					model.addAttribute("products", productService.findAllForOrder(ldto.getBranch_id()));

					return "orderwithdetails/add";
				}

				detail.setOrder_id(order.getOrder_id());
			}

			orderDetailsService.save(dto);

			BigDecimal subtotal = orderDetailsService.calculateTotalAmount(order.getOrder_id());

			BigDecimal discount = orderDetailsService.calculateDiscountAmount(order.getOrder_id());

			BigDecimal finalAmount = subtotal.subtract(discount);

			ordersService.updateTotalAmount(order.getOrder_id(), finalAmount);

			session.removeAttribute("selectedOrderDetails");

			return "redirect:/order-with-details";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("products", productService.findAllForOrder(ldto.getBranch_id()));

			return "orderwithdetails/add";
		}
	}

	@PostMapping("/received/{id}")
	public String received(@PathVariable("id") String id) {

		ordersService.setReceivedTime(id);

		return "redirect:/order-with-details";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, Model model, HttpSession session, RedirectAttributes redirect) {

		if (paymentService.existsByOrderId(id)) {
			redirect.addFlashAttribute("error", "Paid orders cannot be edited.");

			return "redirect:/order-with-details";
		}

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		Orders order = ordersService.findById(id);

		if (order == null) {
			redirect.addFlashAttribute("error", "Order not found.");

			return "redirect:/order-with-details";
		}

		List<OrderDetails> details = orderDetailsService.findByOrderId(id);

		OrderDetailsDto dto = new OrderDetailsDto(details);

		model.addAttribute("order", order);
		model.addAttribute("orderDetailsDto", dto);
		model.addAttribute("products", productService.findAllForOrder(ldto.getBranch_id()));

		return "orderwithdetails/edit";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute("order") Orders order, @ModelAttribute("orderDetailsDto") OrderDetailsDto dto,
			HttpSession session, RedirectAttributes redirect) {

		String orderId = order.getOrder_id();

		if (orderId == null || orderId.trim().isEmpty()) {
			redirect.addFlashAttribute("error", "Invalid order.");

			return "redirect:/order-with-details";
		}

		if (paymentService.existsByOrderId(orderId)) {
			redirect.addFlashAttribute("error", "Paid orders cannot be edited.");

			return "redirect:/order-with-details";
		}

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		order.setEmployee_id(ldto.getEmployee_id());
		order.setBranch_id(ldto.getBranch_id());

		try {
			orderDetailsService.checkStock(dto, order.getBranch_id());

			ordersService.edit(orderId, order);

			orderDetailsService.edit(dto, orderId);

			BigDecimal subtotal = orderDetailsService.calculateTotalAmount(orderId);

			BigDecimal discount = orderDetailsService.calculateDiscountAmount(orderId);

			BigDecimal finalAmount = subtotal.subtract(discount);

			ordersService.updateTotalAmount(orderId, finalAmount);

			redirect.addFlashAttribute("success", "Order updated successfully.");

			return "redirect:/order-with-details";

		} catch (IllegalArgumentException e) {
			redirect.addFlashAttribute("error", e.getMessage());

			return "redirect:/order-with-details/edit/" + orderId;
		}
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {

		ordersService.delete(id);

		return "redirect:/order-with-details";
	}

	@GetMapping("/deleted")
	public String deletedList(Model model) {

		model.addAttribute("orders", ordersService.findDeletedAll());

		model.addAttribute("orderDetails", orderDetailsService.findAll());

		return "orderwithdetails/deletedlist";
	}

	@PostMapping("/restore/{id}")
	public String restore(@PathVariable("id") String id) {

		ordersService.restore(id);

		return "redirect:/order-with-details/deleted";
	}

	@PostMapping("/permanent-delete/{id}")
	public String permanentDelete(@PathVariable("id") String id, RedirectAttributes redirect) {

		if (ordersService.isPaymentDone(id)) {
			redirect.addFlashAttribute("error", "This order has already been paid.");

			return "redirect:/order-with-details";
		}

		ordersService.delete(id);

		redirect.addFlashAttribute("success", "Order cancelled successfully!");

		return "redirect:/order-with-details";
	}
}