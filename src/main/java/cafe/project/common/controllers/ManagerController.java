package cafe.project.common.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.repositories.AtypeAndPtypeRepository;
import cafe.project.repositories.StatusRepository;
import cafe.project.services.BranchService;
import cafe.project.services.CategoryService;
import cafe.project.services.DailyRegisterService;
import cafe.project.services.DiscountService;
import cafe.project.services.EmployeeService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager-only")
public class ManagerController {
	
	private AtypeAndPtypeRepository typeRepo;
	
	public ManagerController(AtypeAndPtypeRepository typeRepo) {
		this.typeRepo = typeRepo;
	}
	
	@GetMapping("/types/audience")
	public String getAudienceTypes(Model model) {
		model.addAttribute("types", typeRepo.findTypesAll(false));
		model.addAttribute("name", "audience");
		model.addAttribute("dname", "Audience");
		return "types/list-manager";
	}
	
	@GetMapping("/types/promo")
	public String getPromoTypes(Model model) {
		model.addAttribute("types", typeRepo.findTypesAll(true));
		model.addAttribute("name", "promo");
		model.addAttribute("dname", "Promo");
		return "types/list-manager";
	}

}
