package cafe.project.HeinMinHtet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.HeinMinHtet.repositories.entities.OrderDetails;
import cafe.project.HeinMinHtet.services.OrderDetailsService;

@Controller
@RequestMapping("/order-details")
public class OrderDetailsController {

	private final OrderDetailsService orderDetailsService;

	public OrderDetailsController(OrderDetailsService orderDetailsService) {

		this.orderDetailsService = orderDetailsService;
	}

	
	// LIST
	
	@GetMapping
	public String index(Model model) {

		model.addAttribute(
				"orderDetails",
				orderDetailsService.findAll());

		return "HeinMinHtet/order-details/index";
	}

	
	// ADD FORM
	
	@GetMapping("/add")
	public String add(Model model) {

		model.addAttribute(
				"orderDetail",
				new OrderDetails());

		return "HeinMinHtet/order-details/add";
	}

	
	// SAVE
	
	@PostMapping("/add")
	public String save(
			@ModelAttribute("orderDetail")
			OrderDetails orderDetail) {

		orderDetailsService.save(orderDetail);

		return "redirect:/order-details";
	}

	// =========================
	// EDIT FORM
	// =========================
	@GetMapping("/edit/{id}")
	public String edit(
			@PathVariable String id,
			Model model) {

		OrderDetails existingOrderDetail =
				orderDetailsService.findById(id);

		if (existingOrderDetail != null) {

			model.addAttribute(
					"orderDetail",
					existingOrderDetail);

			return "HeinMinHtet/order-details/edit";
		}

		return "redirect:/order-details";
	}

	
	// UPDATE
	
	@PostMapping("/edit/{id}")
	public String update(
			@PathVariable String id,
			@ModelAttribute("orderDetail")
			OrderDetails orderDetail) {

		orderDetailsService.edit(id, orderDetail);

		return "redirect:/order-details";
	}

	
	// DELETE
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable String id) {

		orderDetailsService.delete(id);

		return "redirect:/order-details";
	}
}
