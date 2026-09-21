package cafe.project.HeinMinHtet.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cafe.project.HeinMinHtet.repositories.entities.Orders;
import cafe.project.HeinMinHtet.services.OrdersService;

@Controller
@RequestMapping("/orders")
public class OrdersController {

	private final OrdersService ordersService;

	public OrdersController(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	@GetMapping("")
	public String showOrderList(Model model) {
		List<Orders> orders = ordersService.getAllOrders();
		model.addAttribute("orders", orders);
		return "HeinMinHtet/orders/list";
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		Orders order = new Orders();
		model.addAttribute("order", order);
		return "HeinMinHtet/orders/add";
	}

	@PostMapping("/save")
	public String saveOrder(@ModelAttribute("order") Orders order, RedirectAttributes ra) {
		if (ordersService.saveOrder(order)) {
			ra.addFlashAttribute("successMsg", "Order created successfully!");
		} else {
			ra.addFlashAttribute("errorMsg", "Failed to create order.");
		}
		return "redirect:/orders";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String order_id, Model model) {
		Orders order = ordersService.getOrderById(order_id);
		model.addAttribute("order", order);
		return "HeinMinHtet/orders/edit";
	}

	@PostMapping("/update")
	public String updateOrder(@ModelAttribute("order") Orders orderDTO, RedirectAttributes ra) {
		if (ordersService.updateOrder(orderDTO.getOrder_id(), orderDTO)) {
			ra.addFlashAttribute("successMsg", "Order updated successfully!");
		} else {
			ra.addFlashAttribute("errorMsg", "Failed to update order.");
		}
		return "redirect:/orders";
	}

	@GetMapping("/delete-confirm/{id}")
	public String showDeleteConfirmPage(@PathVariable("id") String order_id, Model model) {
		Orders order = ordersService.getOrderById(order_id);
		model.addAttribute("order", order);
		return "HeinMinHtet/orders/delete";
	}

	@PostMapping("/delete")
	public String deleteOrder(@ModelAttribute("order") Orders orderDTO, RedirectAttributes ra) {
		if (ordersService.deleteOrder(orderDTO.getOrder_id())) {
			ra.addFlashAttribute("successMsg", "Order deleted successfully!");
		} else {
			ra.addFlashAttribute("errorMsg", "Failed to delete order.");
		}
		return "redirect:/orders";
	}
}