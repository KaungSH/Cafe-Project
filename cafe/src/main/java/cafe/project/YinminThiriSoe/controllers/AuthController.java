package cafe.project.YinminThiriSoe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cafe.project.YinminThiriSoe.models.employeemanagement.LoginModel;
import cafe.project.YinminThiriSoe.repositories.entities.Employee;
import cafe.project.YinminThiriSoe.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {

	private final EmployeeService employeeService;

	public AuthController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employee/login")
	public String loginPage(Model model) {
		model.addAttribute("loginModel", new LoginModel());
		return "YinminThiriSoe/employee/login";
	}

	@PostMapping("/employee/login")
	public String login(@Valid @ModelAttribute("loginModel") LoginModel loginModel, BindingResult bindingResult,
			HttpSession session, HttpServletRequest request, Model model) {

		if (bindingResult.hasErrors()) {
			return "YinminThiriSoe/employee/login";
		}

		Employee employee = employeeService.login(loginModel.getEmail(), loginModel.getPassword());

		if (employee != null) {
			session.invalidate(); // Session fixation attack ကာကွယ်ရန်
			HttpSession newSession = request.getSession(true);
			newSession.setAttribute("loggedEmployee", employee);
			return "redirect:/employee";
		}

		model.addAttribute("error", "Invalid email or password");
		return "YinminThiriSoe/employee/login";
	}

	@GetMapping("/employee/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/employee/login";
	}
}