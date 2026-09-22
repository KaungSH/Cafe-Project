package cafe.project.employeemanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.employeemanagement.services.EmployeeManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	
	private final EmployeeManagementService userService;
	
	public AuthController(EmployeeManagementService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new LoginDto());
		return "common/user/login";
	}
	
	@PostMapping("/login")
	public String loginPage(@ModelAttribute("user") LoginDto ldto, HttpSession oldsession, HttpServletRequest request, Model model) {
		LoginDto ldto2 = userService.findByLogin(ldto);
		
		if (ldto2 == null) {
			model.addAttribute("error", "Invalid Email or Password.");
			return "common/user/login";
		}
		
		if (!ldto2.getEmployee_status().equals("NORMAL")) {
			model.addAttribute("error", "You Are Not Allowed to Login.");
			return "common/user/login";
		}
		
		oldsession.invalidate();
		HttpSession session = request.getSession(true);
		session.setAttribute("loggedInUser", ldto2);
		System.out.println(ldto2.getEmployee_name());
		System.out.println("SESSION ID: " + session.getId());
		System.out.println("USER: " + session.getAttribute("loggedInUser"));
		return "redirect:/";
	}
	
	
	@GetMapping("/logout")
	public String logoutPage(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
}
