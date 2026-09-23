package cafe.project.NayZarLinn.controllers;

import cafe.project.NayZarLinn.models.ExpenseCategoryDto;
import cafe.project.NayZarLinn.services.ExpenseCategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manager/expensecategory")
public class ExpenseCategoryController {

	private final ExpenseCategoryService expenseCategoryService;

	public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
		this.expenseCategoryService = expenseCategoryService;
	}

	@GetMapping
	public String ExpenseCategoryList(Model model) {
		model.addAttribute("expense", this.expenseCategoryService.findAll());
		return "NayZarLinn/expensecategory/list";
	}

	@GetMapping("/add")
	public String addExpenseCategory(Model model) {
		model.addAttribute("expensecategory", new ExpenseCategoryDto());
		return "NayZarLinn/expensecategory/add";
	}

	@PostMapping("/add")
	public String addSupplier(@Valid @ModelAttribute("expensecategory") ExpenseCategoryDto expensecategory,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "NayZarLinn/expensecategory/add";
		}
		expenseCategoryService.add(expensecategory);
		return "redirect:/manager/expensecategory";
	}

	@GetMapping("/edit/{expense_category_id}")
	public String editExpensecategory(@PathVariable String expense_category_id, Model model) {
		ExpenseCategoryDto existingEC = this.expenseCategoryService.findById(expense_category_id);
		if (existingEC != null) {
			model.addAttribute("expensecategory", existingEC);
			return "NayZarLinn/expensecategory/edit";
		}
		return "redirect:/manager/expensecategory";
	}

	@PostMapping("/edit")
	public String editExpensecategory(@Valid @ModelAttribute("expensecategory") ExpenseCategoryDto expensecategory,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "NayZarLinn/ExpenseCategoryDto/edit";
		}
		this.expenseCategoryService.edit(expensecategory.getExpense_category_id(), expensecategory);
		return "redirect:/manager/expensecategory";
	}

	@PostMapping("/delete/{expense_category_id}")
	public String Delete(@PathVariable String expense_category_id) {
		expenseCategoryService.delete(expense_category_id);
		return "redirect:/manager/expensecategory";
	}

}
