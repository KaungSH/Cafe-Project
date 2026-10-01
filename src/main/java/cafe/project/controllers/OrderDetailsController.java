package cafe.project.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.OrderDetailsDto;
import cafe.project.repositories.entities.OrderDetails;
import cafe.project.repositories.entities.Orders;
import cafe.project.services.BranchService;
import cafe.project.services.EmployeeService;
import cafe.project.services.OrderDetailsService;
import cafe.project.services.OrdersService;
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

	public OrderDetailsController(OrderDetailsService orderDetailsService, ProductService productService,
			OrdersService ordersService, BranchService branchService, EmployeeService employeeService) {

		this.orderDetailsService = orderDetailsService;
		this.productService = productService;
		this.ordersService = ordersService;
		this.branchService = branchService;
		this.employeeService = employeeService;
	}

	@GetMapping
	public String list(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("orders", ordersService.findAll());

		model.addAttribute("orderDetails", orderDetailsService.findAll());

		return "orderwithdetails/list";
	}

	@GetMapping("/add")
	public String add(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		Orders order = new Orders();
		order.setEmployee_id(ldto.getEmployee_id());
		order.setBranch_id(ldto.getBranch_id());

		OrderDetailsDto dto = new OrderDetailsDto();

		model.addAttribute("order", order);

		model.addAttribute("orderDetailsDto", dto);

		model.addAttribute("products", productService.findAll());

		return "orderwithdetails/add";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("order") Orders order, @ModelAttribute("orderDetailsDto") OrderDetailsDto dto,
			HttpSession session) {

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		order.setEmployee_id(ldto.getEmployee_id());
		order.setBranch_id(ldto.getBranch_id());

		ordersService.save(order);

		for (OrderDetails detail : dto.getOrderDetails()) {

			System.out.println("Product ID = " + detail.getProduct_id());
			System.out.println("Quantity = " + detail.getQuantity());
			System.out.println("Remark = " + detail.getRemark());

			if (detail.getProduct_id() == null || detail.getProduct_id().trim().isEmpty()) {

				System.out.println("ERROR: Product ID is null!");

				return "redirect:/order-with-details/add";
			}

			detail.setOrder_id(order.getOrder_id());
		}

		orderDetailsService.save(dto);

		return "redirect:/order-with-details";
	}

	// EDIT FORM
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, Model model) {

		Orders order = ordersService.findById(id);

		List<OrderDetails> details = orderDetailsService.findByOrderId(id);

		OrderDetailsDto dto = new OrderDetailsDto(details);

		model.addAttribute("order", order);
		model.addAttribute("orderDetailsDto", dto);

		model.addAttribute("products", productService.findAll());

		return "orderwithdetails/edit";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute("order") Orders order, @ModelAttribute("orderDetailsDto") OrderDetailsDto dto,
			HttpSession session) {

		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		order.setEmployee_id(ldto.getEmployee_id());
		order.setBranch_id(ldto.getBranch_id());

		String orderId = order.getOrder_id();

		System.out.println("UPDATE ORDER ID = " + orderId);

		ordersService.edit(orderId, order);

		orderDetailsService.edit(dto, orderId);

		return "redirect:/order-with-details";
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
	public String permanentDelete(@PathVariable("id") String id) {

		ordersService.permanentDelete(id);

		return "redirect:/order-with-details/deleted";
	}
}