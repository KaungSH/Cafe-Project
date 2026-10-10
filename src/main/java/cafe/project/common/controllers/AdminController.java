package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.BranchEntryDto;
import cafe.project.repositories.StatusRepository;
import cafe.project.services.BranchService;
import cafe.project.services.CategoryService;
import cafe.project.services.DailyRegisterService;
import cafe.project.services.DiscountService;
import cafe.project.services.EmployeeService;
import cafe.project.services.ExpenseService;
import cafe.project.services.WasteLogsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final DailyRegisterService dailyRegisterservice;
	private final CategoryService categoryService;
	private final DiscountService discountService;
	private final BranchService branchService;
	private final EmployeeService employeeService;
	private final StatusRepository statusRepository;
	private final ExpenseService expenseService;
	private final WasteLogsService wasteLogsService;

	public AdminController(DailyRegisterService dailyRegisterservice, BranchService branchService, EmployeeService employeeService, StatusRepository statusRepository, CategoryService categoryService, DiscountService discountService, ExpenseService expenseService, WasteLogsService wasteLogsService) {
		this.expenseService = expenseService;
		this.dailyRegisterservice = dailyRegisterservice;
		this.categoryService = categoryService;
		this.branchService = branchService;
		this.employeeService = employeeService;
		this.statusRepository = statusRepository;
		this.discountService = discountService;
		this.wasteLogsService = wasteLogsService;
	}
	
	@GetMapping("/daily-registers")
	public String dailyRegisterList(Model model) {
		model.addAttribute("registers", dailyRegisterservice.getAllRegistersAdmin());
		return "daily_registers/list-admin";
	}
	@GetMapping("/ingredient-batches")
	public String ingredientbatches(Model model) {
		model.addAttribute("registers", dailyRegisterservice.getAllRegistersAdmin());
		return "ingredient-batches/list-admin";
	}
	
	@GetMapping("/categories")
	public String categoriesList(Model model) {
		model.addAttribute("categories", categoryService.findAllByRelationAdmin());
		return "categories/list-admin";
	}
	
	@GetMapping("/discounts")
	public String discountList(Model model) {
		model.addAttribute("discounts", discountService.getAllDiscountsAdmin());
		return "discount/list-admin-staff";
	}
	
	@GetMapping("/branches")
	public String list(Model model) {
		model.addAttribute("branches", branchService.findAll());
		return "branches/list";
	}
	@GetMapping("/branches/create")
	public String showCreateForm(Model model) {
		model.addAttribute("branchDto", new BranchEntryDto());
		model.addAttribute("statuses", branchService.findAllStatuses());
		return "branches/create";
	}
	@PostMapping("/branches/create")
	public String save(@Valid @ModelAttribute("branchDto") BranchEntryDto dto, BindingResult result, Model model) {
		validateTimeRange(dto, result);
		if (result.hasErrors()) {
			model.addAttribute("statuses", branchService.findAllStatuses());
			return "branches/create";
		}
		branchService.add(dto);
		return "redirect:/admin/branches";
	}
	
	private void validateTimeRange(BranchEntryDto dto, BindingResult result) {
		if (dto.getOpening_time() != null && dto.getClosing_time() != null) {
			if (dto.getClosing_time().isBefore(dto.getOpening_time())) {
				result.rejectValue("closing_time", "error.branchDto", 
						"The cl3osing time should not be later than the opening time.");
			}
			if (dto.getClosing_time().equals(dto.getOpening_time())) {
				result.rejectValue("closing_time", "error.branchDto",
						"The opening and closing times must not be the same.");
			}
		}
	}
	
	@GetMapping("/expenses")
	public String ExpenseList(Model model) {
		model.addAttribute("expense", this.expenseService.findAllAdmin());
		return "expense/list-admin";
	}
	
	@GetMapping("/wastelogs")
	public String listWasteLogs(Model model) {
		model.addAttribute("wasteLogs", wasteLogsService.getAllWasteLogsAdmin());
		return "WasteLogs/list-admin";
	}
	
	@GetMapping("branches/edit/{branch_id}")
	public String branchEdit(@PathVariable("branch_id") String branch_id, Model model) {
		BranchEntryDto dto = branchService.findById(branch_id);
		if (dto == null) {
			return "redirect:/admin/branches";
		}
		model.addAttribute("branchDto", dto);
		model.addAttribute("statuses", branchService.findAllStatuses());
		return "branches/edit";
	}
	@PostMapping("branches/edit/{branch_id}")
	public String branchEdit(@PathVariable("branch_id") String branch_id, @Valid @ModelAttribute("branchDto") BranchEntryDto dto,
			BindingResult result, Model model) {
			validateTimeRange(dto, result);
		if (result.hasErrors()) {
			model.addAttribute("statuses", branchService.findAllStatuses());
			return "branches/edit";
		}
		branchService.edit(branch_id, dto);
		return "redirect:/admin/branches";
	}

}
