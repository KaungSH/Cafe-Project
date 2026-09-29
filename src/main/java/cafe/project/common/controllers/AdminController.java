package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.repositories.StatusRepository;
import cafe.project.services.BranchService;
import cafe.project.services.CategoryService;
import cafe.project.services.DailyRegisterService;
import cafe.project.services.DiscountService;
import cafe.project.services.EmployeeService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final DailyRegisterService dailyRegisterservice;
	private final CategoryService categoryService;
	private final DiscountService discountService;
	private final BranchService branchService;
	private final EmployeeService employeeService;
	private final StatusRepository statusRepository;

	public AdminController(DailyRegisterService dailyRegisterservice, BranchService branchService, EmployeeService employeeService, StatusRepository statusRepository, CategoryService categoryService, DiscountService discountService) {
		this.dailyRegisterservice = dailyRegisterservice;
		this.categoryService = categoryService;
		this.branchService = branchService;
		this.employeeService = employeeService;
		this.statusRepository = statusRepository;
		this.discountService = discountService;
	}
	
	@GetMapping("/daily-registers")
	public String dailyRegisterList(Model model) {
		model.addAttribute("registers", dailyRegisterservice.getAllRegistersAdmin());
		return "daily_registers/list-admin";
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

}
