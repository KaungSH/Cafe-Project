package cafe.project.YinminThiriSoe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cafe.project.YinminThiriSoe.models.employeemanagement.LoginModel;
import cafe.project.YinminThiriSoe.repositories.entities.Employee;
import cafe.project.YinminThiriSoe.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employee")
	public String listEmployees(Model model) {
		model.addAttribute("employee", employeeService.getAllEmployees());
		return "YinminThiriSoe/employee/list";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginModel", new LoginModel());
		return "YinminThiriSoe/employee/login";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginModel") LoginModel loginModel,
			org.springframework.validation.BindingResult bindingResult, HttpSession session, HttpServletRequest request,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "YinminThiriSoe/employee/login";
		}

		Employee employee = employeeService.login(loginModel.getEmail(), loginModel.getPassword());

		if (employee != null) {
			session.invalidate();
			HttpSession newSession = request.getSession(true);
			newSession.setAttribute("loggedEmployee", employee);
			newSession.setAttribute("roleId", employee.getEmployeeRoleId());
			return "redirect:/employee";
		}

		model.addAttribute("error", "Invalid email or password");
		return "YinminThiriSoe/employee/login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/employee/login";
	}

	@GetMapping("/employee/add")
	public String showAddForm(Model model) {
		model.addAttribute("employee", new Employee());
		return "YinminThiriSoe/employee/form";
	}

	@PostMapping("/employee/add")
	public String addEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.createEmployee(employee);
		return "redirect:/employee";
	}

	@GetMapping("/employee/edit/{employee_id}")
	public String showEditForm(@PathVariable("employee_id") String employee_id, Model model) {
		Employee employee = employeeService.getEmployeeById(employee_id);
		model.addAttribute("employee", employee);
		return "YinminThiriSoe/employee/form";
	}

	@PostMapping("/employee/edit/{employee_id}")
	public String editEmployee(@PathVariable("employee_id") String employee_id,
			@ModelAttribute("employee") Employee employee) {
		employee.setEmployeeId(employee_id);
		employeeService.updateEmployee(employee);
		return "redirect:/employee";
	}

	@GetMapping("/employee/delete/{employee_id}")
	public String deleteEmployee(@PathVariable("employee_id") String employee_id) {
		employeeService.deleteEmployee(employee_id);
		return "redirect:/employee";
	}
}