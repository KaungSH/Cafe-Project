package cafe.project.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.DiscountEntryModel;
import cafe.project.models.DiscountListModel;
import cafe.project.models.DiscountProductDto;
import cafe.project.models.ProductListModel;
import cafe.project.repositories.AtypeAndPtypeRepository;
import cafe.project.services.BranchService;
import cafe.project.services.DiscountProductService;
import cafe.project.services.DiscountService;
import cafe.project.services.ProductService;
import jakarta.servlet.http.HttpSession;

import java.util.List;



@Controller
@RequestMapping("/manager-only/discounts")
public class DiscountController {

	private final DiscountService discountService;
	private final DiscountProductService dpService;
	private final AtypeAndPtypeRepository repo;
	private final BranchService branchService;
	private final ProductService productService;

	public DiscountController(DiscountService discountService, DiscountProductService dpService, AtypeAndPtypeRepository repo, BranchService branchService, ProductService productService) {
		this.discountService = discountService;
		this.dpService = dpService;
		this.repo = repo;
		this.branchService=branchService;
		this.productService = productService;
	}

	@GetMapping
	public String listDiscounts(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		List<DiscountListModel> discounts = discountService.getAllDiscounts(ldto.getBranch_id());
		model.addAttribute("discounts", discounts);
		return "discount/list";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("discountForm", new DiscountEntryModel());

		model.addAttribute("promoTypes", repo.findTypesAll(true));

		model.addAttribute("audienceTypes", repo.findTypesAll(false));
		
		model.addAttribute("branch", branchService.findById(ldto.getBranch_id()).getName());

		return "discount/create";
	}

	@PostMapping("/save")
	public String saveDiscount(@ModelAttribute("discountForm") DiscountEntryModel model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.setBranches_branch_id(ldto.getBranch_id());
		model.setEmployee_id(ldto.getEmployee_id());
		if (model.getDiscount_id() == null || model.getDiscount_id().trim().isEmpty()) {
			discountService.createDiscount(model);
		} else {
			discountService.updateDiscount(model);
		}
		return "redirect:/manager-only/discounts";
	}
	
	@GetMapping("/discountProducts/add/{id}")
	public String add(@PathVariable("id") String id, Model model) {
		List<String> existingProductIds = discountService.getAllDiscountsById(id).getProducts().stream().map(ProductListModel::getProduct_id).toList();

		model.addAttribute("existingProductIds", existingProductIds);
		model.addAttribute("discountProduct", new DiscountProductDto());
		model.addAttribute("discount", discountService.getAllDiscountsById(id));
		model.addAttribute("products", productService.findAll());
		return "discount/product-add";
	}

	@PostMapping("/discountProducts/add")
	public String add(@ModelAttribute("discountProduct") DiscountProductDto discountProduct,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "discount/product-add";
		}
		this.dpService.add(discountProduct);
		return "redirect:/manager-only/discounts";
	}
	
	@GetMapping("/discountProducts/delete/{discount_id}/{product_id}")
	public String delete(@PathVariable("discount_id") String discount_id, @PathVariable("product_id") String product_id) {

		dpService.remove(discount_id, product_id);

		return "redirect:/manager-only/discounts";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		DiscountEntryModel discountModel = discountService.getDiscountById(id);
		if(!discountModel.getBranches_branch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT YOUR BRANCH!!!");
			//error
		}
		model.addAttribute("discountForm", discountModel);
		model.addAttribute("branch", branchService.findById(ldto.getBranch_id()).getName());
		model.addAttribute("promoTypes", repo.findTypesAll(true));
		model.addAttribute("audienceTypes", repo.findTypesAll(false));

		return "discount/edit";
	}

	@GetMapping("/delete/{id}")
	public String deleteDiscount(@PathVariable("id") String id) {
		discountService.deleteDiscount(id);
		return "redirect:/manager-only/discounts";
	}
	
	@GetMapping("deleted-list")
	public String deleteDiscountList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("discounts", this.discountService.DeletedList(ldto.getBranch_id()));
		return "discount/deleted-list";
	}
	
	@PostMapping("/restore")
	public String restoreDiscount(@RequestParam String id) {
		discountService.restore(id);
		return "redirect:/manager-only/discounts/deleted-list";
	}
	
	@PostMapping("/real-delete")
	public String realDeleteDiscount(@RequestParam String id) {
		discountService.hardDelete(id);
		return "redirect:/manager-only/discounts/deleted-list";
	}
}