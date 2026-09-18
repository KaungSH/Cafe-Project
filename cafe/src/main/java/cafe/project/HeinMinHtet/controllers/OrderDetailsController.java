package cafe.project.HeinMinHtet.controllers;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.HeinMinHtet.repositories.entities.OrderDetails;
import cafe.project.HeinMinHtet.services.OrderDetailsService;

import cafe.project.KaungSattHein.repositories.entities.ProductsAndQuantities;

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

	
	// EDIT FORM
	
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
	public ProductsAndQuantities fakeFindAll(){
		List<String> Product_ids = new ArrayList<String>();
		String id1 = "fc98683c-e6c1-48e9-abb2-920238a63f7c";
		String id2 = "bc201c0b-3b6c-4ab4-a5f8-3e5e6348cf5c";
		String id3 = "bc201c0b-3b6c-4ab4-a5f8-3e5e6348cf5c";
		Product_ids.add(id1);
		Product_ids.add(id2);
		Product_ids.add(id3);
		List<String> remarks = new ArrayList<String>();
		String remark1 = "Fake Remark 1";
		String remark2 = "Fake Remark 2";
		String remark3 = "Fake Remark 3";
		remarks.add(remark1);
		remarks.add(remark2);
		remarks.add(remark3);
		ProductsAndQuantities pandq = new ProductsAndQuantities();
		pandq.setProduct_ids(Product_ids);
		pandq.setRemarks(remarks);
		List<Integer> quantities = new ArrayList<Integer>();
		Integer i1 = 1;
		Integer i2 = 5;
		Integer i3 = 3;
		quantities.add(i1);
		quantities.add(i2);
		quantities.add(i3);
		pandq.setQuantities(quantities);
		return pandq;
		}
	@GetMapping("/staff/order-details/add")
	public String OrderDetailsAdd(Model model) {
		OrderDetails orderDetails = new OrderDetails();
		
		orderDetails.setOrder_id("1");
		orderDetails.setPandq(fakeFindAll());
		orderDetailsService.save(orderDetails);
		
		return "redirect:/order-details";
}
}
