package cafe.project.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.ExpenseDto;
import cafe.project.repositories.ExpenseCategoryRepository;
import cafe.project.services.BranchService;
import cafe.project.services.ExpenseService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manager-only/expense")
public class ExpenseController {

	private final ExpenseService expenseService;
	private final BranchService branchService;
	private final ExpenseCategoryRepository expenseCategoryRepository;

	public ExpenseController(ExpenseService expenseService, ExpenseCategoryRepository expenseCategoryRepository,
			BranchService branchService) {
		this.expenseService = expenseService;
		this.expenseCategoryRepository = expenseCategoryRepository;
		this.branchService = branchService;
	}

	@GetMapping
	public String ExpenseList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("expense", this.expenseService.findAll(ldto.getBranch_id()));
		return "expense/list";
	}

	@GetMapping("/add")
	public String addExpense(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("expense", new ExpenseDto());
		model.addAttribute("branch", branchService.findById(ldto.getBranch_id()).getName());
		model.addAttribute("expenseCategories", expenseCategoryRepository.findAll());
		return "expense/add";
	}

	@PostMapping("/add")
	public String addExpense(@Valid @ModelAttribute("expense") ExpenseDto expense, BindingResult bindingResult,
			Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		if (bindingResult.hasErrors()) {
			model.addAttribute("branch", branchService.findById(ldto.getBranch_id()).getName());
			model.addAttribute("expenseCategories", expenseCategoryRepository.findAll());
			return "expense/add";
		}
		expense.setBranch_id(ldto.getBranch_id());
		expense.setEmployee_id(ldto.getEmployee_id());
		expense.setCreated_at(LocalDateTime.now());
		expenseService.add(expense);
		return "redirect:/manager-only/expense";
	}

	@GetMapping("/edit/{expense_id}")
	public String editExpense(@PathVariable String expense_id, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		ExpenseDto existingEp = expenseService.findById(expense_id, ldto.getBranch_id());

		if (!existingEp.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		model.addAttribute("expense", existingEp);
		model.addAttribute("branch", branchService.findById(ldto.getBranch_id()).getName());
		model.addAttribute("expenseCategories", expenseCategoryRepository.findAll());
		return "expense/edit";
	}

	@PostMapping("/edit")
	public String editExpense(@Valid @ModelAttribute("expense") ExpenseDto expense, BindingResult bindingResult,
			Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");

		ExpenseDto existingEp = expenseService.findById(expense.getExpense_id(), ldto.getBranch_id());

		if (!existingEp.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("branch", branchService.findById(ldto.getBranch_id()).getName());
			model.addAttribute("expenseCategories", expenseCategoryRepository.findAll());
			return "expense/edit";
		}

		expense.setBranch_id(ldto.getBranch_id());
		expense.setEmployee_id(ldto.getEmployee_id());
		expense.setCreated_at(LocalDateTime.now());
		expenseService.edit(expense.getExpense_id(), expense);
		return "redirect:/manager-only/expense";
	}

	@PostMapping("/delete")
	public String deletedExpense(@RequestParam String expense_id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		ExpenseDto existingEp = expenseService.findById(expense_id, ldto.getBranch_id());

		if (!existingEp.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		expenseService.delete(expense_id);
		return "redirect:/manager-only/expense";
	}

	@GetMapping("/deleted")
	public String deletedExpenseList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("expense", expenseService.deletedList(ldto.getBranch_id()));
		return "expense/deletedList";
	}

	@PostMapping("/restore")
	public String restoreSupplier(@RequestParam String expense_id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		ExpenseDto existingEp = expenseService.findById(expense_id, ldto.getBranch_id());

		if (!existingEp.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Your Branch's Data!!!");
		}

		expenseService.restore(expense_id);
		return "redirect:/manager-only/expense/deleted";
	}

	@PostMapping("/real-delete")
	public String realDeleteExpense(@RequestParam String expense_id, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		ExpenseDto existingEp = expenseService.findById(expense_id, ldto.getBranch_id());

		if (!existingEp.getBranch_id().equals(ldto.getBranch_id())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not Your Branch's Data!!!");
		}

		expenseService.hardDelete(expense_id);
		return "redirect:/manager-only/expense/deleted";
	}
}