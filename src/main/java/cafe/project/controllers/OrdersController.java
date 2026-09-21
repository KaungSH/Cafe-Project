package cafe.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.repositories.entities.Orders;
import cafe.project.services.OrdersService;

@Controller
@RequestMapping("/orders")
public class OrdersController {

	private final OrdersService ordersService;

	public OrdersController(OrdersService ordersService) {
		this.ordersService = ordersService;
	}


	// LIST
	
	@GetMapping
	public String index(Model model) {

		model.addAttribute("orders", ordersService.findAll());

		return "HeinMinHtet/orders/index";
	}

	
	// ADD FORM
	
	@GetMapping("/add")
	public String add(Model model) {

		model.addAttribute("order", new Orders());

		return "HeinMinHtet/orders/add";
	}

	// =========================
	// SAVE
	// =========================
	@PostMapping("/add")
	public String save(@ModelAttribute("order") Orders order) {

		ordersService.save(order);

		return "redirect:/orders";
	}

	// =========================
	// EDIT FORM
	// =========================
	@GetMapping("/edit/{id}")
	public String edit(
			@PathVariable String id,
			Model model) {

		Orders existingOrder = ordersService.findById(id);

		if (existingOrder != null) {
			model.addAttribute("order", existingOrder);

			return "HeinMinHtet/orders/edit";
		}

		return "redirect:/orders";
	}

	
	// UPDATE
	
	@PostMapping("/edit/{id}")
	public String update(
			@PathVariable String id,
			@ModelAttribute("order") Orders order) {

		ordersService.edit(id, order);

		return "redirect:/orders";
	}

	
	// DELETE
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable String id) {

		ordersService.delete(id);

		return "redirect:/orders";
	}
}
