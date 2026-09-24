package cafe.project.controllers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe.project.models.ExpenseDto;
import cafe.project.repositories.BranchRepository;
import cafe.project.repositories.ExpenseCategoryRepository;
import cafe.project.services.ExpenseService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manager/expense")
public class ExpenseController {

	private final ExpenseService expenseService;
	private final BranchRepository branchRepository;
	private final ExpenseCategoryRepository expenseCategoryRepository;

	public ExpenseController(ExpenseService expenseService, BranchRepository branchRepository,
			ExpenseCategoryRepository expenseCategoryRepository) {
		this.expenseService = expenseService;
		this.branchRepository = branchRepository;
		this.expenseCategoryRepository = expenseCategoryRepository;
	}

	@GetMapping
	public String ExpenseList(Model model) {
		model.addAttribute("expense", this.expenseService.findAll());
		return "NayzarLinn/expense/list";
	}

	@GetMapping("/add")
	public String addExpense(Model model) {
		model.addAttribute("expense", new ExpenseDto());
		model.addAttribute("branches", branchRepository.findAll());
		model.addAttribute("expenseCategories", expenseCategoryRepository.findAll());
		return "NayZarLinn/expense/add";
	}

	@PostMapping("/add")
	public String addExpense(@Valid @ModelAttribute("expense") ExpenseDto expense, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("branches", branchRepository.findAll());
			model.addAttribute("expenseCategories", expenseCategoryRepository.findAll());
			return "NayZarLinn/expense/add";
		}
		expense.setCreated_at(LocalDateTime.now());
		expenseService.add(expense);
		return "redirect:/manager/expense";
	}

	@GetMapping("/edit/{expense_id}")
	public String editExpense(@PathVariable String expense_id, Model model) {
		ExpenseDto existingEp = expenseService.findById(expense_id);
		if (existingEp != null) {
			model.addAttribute("expense", existingEp);
			model.addAttribute("branches", branchRepository.findAll());
			model.addAttribute("expenseCategories", expenseCategoryRepository.findAll());
			return "NayZarLinn/expense/edit";
		}
		return "redirect:/manager/expense";
	}

	@PostMapping("/edit")
	public String editExpense(@Valid @ModelAttribute("expense") ExpenseDto expense, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("branches", branchRepository.findAll());
			model.addAttribute("expenseCategories", expenseCategoryRepository.findAll());
			return "NayZarLinn/expense/edit";
		}
		expense.setCreated_at(LocalDateTime.now());
		expenseService.edit(expense.getExpense_id(), expense);
		return "redirect:/manager/expense";
	}

	@PostMapping("/delete")
	public String deletedExpense(@RequestParam String expense_id) {
		expenseService.delete(expense_id);
		return "redirect:/manager/expense";
	}

	@GetMapping("/deleted")
	public String deletedExpenseList(Model model) {
		model.addAttribute("expense", expenseService.deletedList());
		return "NayZarLinn/expense/deletedList";
	}

	@PostMapping("/restore")
	public String restoreSupplier(@RequestParam String expense_id) {
		expenseService.restore(expense_id);
		return "redirect:/manager/expense/deleted";
	}

	@PostMapping("/real-delete")
	public String realDeleteExpense(@ModelAttribute("expense") ExpenseDto expense) {
		expenseService.hardDelete(expense.getExpense_id());
		return "redirect:/manager/expense/deleted";
	}

}
