package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.repositories.StatusRepository;
import cafe.project.services.BranchService;
import cafe.project.services.CategoryService;
import cafe.project.services.DailyRegisterService;
import cafe.project.services.DiscountService;
import cafe.project.services.EmployeeService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/staff")
public class StaffController {

	private final DiscountService discountService;
	
	public StaffController(DiscountService discountService) {
		this.discountService = discountService;
	}
	
	@GetMapping("/discounts")
	public String discountList(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("discounts", discountService.getAllDiscounts(ldto.getBranch_id()));
		return "discount/list-admin-staff";
	}

}
