package cafe.project.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.models.DiscountProductDto;
import cafe.project.services.DiscountProductService;
import cafe.project.services.DiscountService;
import cafe.project.services.ProductService;

@Controller
public class DiscountProductController {

	private final DiscountProductService dpService;
	private final DiscountService discountService;
	private final ProductService productService;

	public DiscountProductController(DiscountProductService dpService, DiscountService discountService,
			ProductService productService) {

		this.dpService = dpService;
		this.discountService = discountService;
		this.productService = productService;
	}

	@GetMapping("/discountProducts")
	public String list(Model model) {
		model.addAttribute("discountProducts", dpService.findAll());
		return "discount_product/list";
	}

	@GetMapping("/discountProducts/add")
	public String add(Model model) {
		model.addAttribute("discountProduct", new DiscountProductDto());
		model.addAttribute("discounts", discountService.getAllDiscounts());
		model.addAttribute("products", productService.findAll());
		return "discount_product/add";
	}

	@PostMapping("/discountProducts/add")
	public String add(@ModelAttribute("discountProduct") DiscountProductDto discountProduct,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/discount_product/add";
		}
		this.dpService.add(discountProduct);
		return "redirect:/discountProducts";
	}

	@PostMapping("/discountProducts/delete")
	public String delete(@RequestParam String discount_id) {

		dpService.deleteByDiscountId(discount_id);

		return "redirect:/discountProducts";
	}
}
