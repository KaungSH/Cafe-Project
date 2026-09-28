package cafe.project.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cafe.project.models.DiscountEntryModel;
import cafe.project.models.DiscountListModel;
import cafe.project.repositories.AtypeAndPtypeRepository;
import cafe.project.services.BranchService;
import cafe.project.services.DiscountService;

import java.util.List;



@Controller
@RequestMapping("/manager/discounts")
public class DiscountController {

	private final DiscountService discountService;
	private final AtypeAndPtypeRepository repo;
	private final BranchService branchService;

	public DiscountController(DiscountService discountService, AtypeAndPtypeRepository repo, BranchService branchService) {
		this.discountService = discountService;
		this.repo = repo;
		this.branchService=branchService;
	}

	@GetMapping
	public String listDiscounts(Model model) {
		List<DiscountListModel> discounts = discountService.getAllDiscounts();
		model.addAttribute("discounts", discounts);
		return "discount/list";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("discountForm", new DiscountEntryModel());

		model.addAttribute("promoTypes", repo.findTypesAll(true));

		model.addAttribute("audienceTypes", repo.findTypesAll(false));
		
		model.addAttribute("branches",branchService.findAll());

		return "discount/create";
	}

	@PostMapping("/save")
	public String saveDiscount(@ModelAttribute("discountForm") DiscountEntryModel model) {
		if (model.getDiscount_id() == null || model.getDiscount_id().trim().isEmpty()) {
			discountService.createDiscount(model);
		} else {
			discountService.updateDiscount(model);
		}
		return "redirect:/manager/discounts";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model) {
		DiscountEntryModel discountModel = discountService.getDiscountById(id);
		model.addAttribute("discountForm", discountModel);

		model.addAttribute("promoTypes", repo.findTypesAll(true));
		model.addAttribute("audienceTypes", repo.findTypesAll(false));

		return "discount/edit";
	}

	@GetMapping("/delete/{id}")
	public String deleteDiscount(@PathVariable("id") String id) {
		discountService.deleteDiscount(id);
		return "redirect:/manager/discounts";
	}
	
	@GetMapping("deleted-list")
	public String deleteDiscountList(Model model) {
		model.addAttribute("discounts", this.discountService.DeletedList());
		return "discount/deleted-list";
	}
	
	@PostMapping("/restore")
	public String restoreDiscount(@RequestParam String id) {
		discountService.restore(id);
		return "redirect:/manager/discounts/deleted-list";
	}
	
	@PostMapping("/real-delete")
	public String realDeleteDiscount(@RequestParam String id) {
		discountService.hardDelete(id);
		return "redirect:/manager/discounts/deleted-list";
	}
}