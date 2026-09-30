package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.models.OrderDetailsDto;
import cafe.project.services.OrderDetailsService;
import cafe.project.services.ProductService;

@Controller
@RequestMapping("/order-details")
public class OrderDetailsController {

	private final OrderDetailsService orderDetailsService;
	private final ProductService productService;

	public OrderDetailsController(OrderDetailsService orderDetailsService, ProductService productService) {

		this.orderDetailsService = orderDetailsService;
		this.productService = productService;
	}

	// LIST
	@GetMapping
	public String list(Model model) {

		model.addAttribute("orderDetails", orderDetailsService.findAll());

		return "orderdetails/list";
	}

	// ADD FORM
	@GetMapping("/add")
	public String add(Model model) {

		model.addAttribute("orderDetailsDto", new OrderDetailsDto());

		model.addAttribute("orders", orderDetailsService.findOrderIds());

		model.addAttribute("products", productService.findAll());

		return "orderdetails/add";
	}

	// INSERT - LIST
	@PostMapping("/save")
	public String save(@ModelAttribute("orderDetailsDto") OrderDetailsDto dto) {

		orderDetailsService.save(dto);

		return "redirect:/order-details";
	}

	// EDIT FORM
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, Model model) {

		model.addAttribute("orderDetail", orderDetailsService.findById(id));

		return "orderdetails/edit";
	}

	// UPDATE - LIST
	@PostMapping("/update")
	public String update(@ModelAttribute("orderDetailsDto") OrderDetailsDto dto) {

		orderDetailsService.edit(dto);

		return "redirect:/order-details";
	}

	// DELETE - LIST
	@PostMapping("/delete")
	public String delete(@ModelAttribute("orderDetailsDto") OrderDetailsDto dto) {

		orderDetailsService.delete(dto);

		return "redirect:/order-details";
	}

	// LIST BY ORDER ID
	@GetMapping("/order/{orderId}")
	public String findByOrderId(@PathVariable("orderId") String orderId, Model model) {

		model.addAttribute("orderDetails", orderDetailsService.findByOrderId(orderId));

		return "orderdetails/list";
	}
}