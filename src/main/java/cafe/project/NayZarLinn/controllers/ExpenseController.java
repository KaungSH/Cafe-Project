package cafe.project.NayZarLinn.controllers;

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

import cafe.project.NayZarLinn.models.ExpenseDto;
import cafe.project.NayZarLinn.services.ExpenseService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manager/expense")
public class ExpenseController {

	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
	@GetMapping
	public String ExpenseList(Model model) {
		model.addAttribute("expense", this.expenseService.findAll());
		return "NayzarLinn/expense/list";
	}
	
	@GetMapping("/add")
	public String addExpense(Model model) {
		model.addAttribute("expense", new ExpenseDto());
		return "NayZarLinn/expense/add";
	}
	
	@PostMapping("/add")
	public String addExpense(@Valid @ModelAttribute("expense") ExpenseDto expense,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "NayZarLinn/expense/add";
		}
		expense.setCreated_at(LocalDateTime.now());
		this.expenseService.add(expense);
		return "redirect:/manager/expense";
	}
	
	@GetMapping("/edit/{expense_id}")
	public String editExpense(@PathVariable String expense_id,Model model) {
		ExpenseDto existingEp = this.expenseService.findById(expense_id);
		if(existingEp != null) {
			model.addAttribute("expense",existingEp);
			return "NayZarLinn/Expense/edit";
		}
		return "redirect:/error/404";
	}
	
	@PostMapping("edit")
	public String editExpense(@Valid @ModelAttribute("expense") ExpenseDto expense,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "NayZarLinn/expense/edit";
		}
		expense.setCreated_at(LocalDateTime.now());
		this.expenseService.edit(expense.getExpense_id(), expense);
		return "redirect:/error/404";
	}
	
	@GetMapping("delete/{expense_id}")
	public String deleteExpense(@PathVariable String expense_id,Model model) {
		ExpenseDto existingEp = this.expenseService.findById(expense_id);
		if(existingEp != null) {
			model.addAttribute("expense", existingEp);
			return "NayZarLinn/expense/delete";
		}
		model.addAttribute("expense", existingEp);
		return "redirect:/error/404";
	}
	
	@PostMapping("/delete")
	public String deletedExpense(@ModelAttribute("expense") ExpenseDto expense) {
		this.expenseService.delete(expense.getExpense_id());
		return "redirct:/manager/expense";
	}
	
	@GetMapping("/deleted")
	public String deletedExpenseList(Model model) {
		model.addAttribute("expense", expenseService.deletedList());
		return "NayZarLinn/expense/deletedList";
	}
	
	@PostMapping("restore")
	public String restoreSupplier(@RequestParam String expense_id) {
		expenseService.restore(expense_id);
		return "redirect:/manager/expense/deleted";
	}
	
	@PostMapping("real-delete")
	public String realDeleteExpense(@ModelAttribute("expense") ExpenseDto expense) {
		expenseService.hardDelete(expense.getExpense_id());
		return "redirect:/manager/expense/deleted";
	}
	
}
