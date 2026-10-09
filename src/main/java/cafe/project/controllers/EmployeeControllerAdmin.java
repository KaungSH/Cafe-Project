package cafe.project.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.models.EmployeeEntryDto;
import cafe.project.models.Gender;
import cafe.project.models.StatusDto;
import cafe.project.repositories.EmployeeRepository;
import cafe.project.repositories.StatusRepository;
import cafe.project.services.BranchService;
import cafe.project.services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/employee")
public class EmployeeControllerAdmin{

	private final EmployeeService employeeService;
	private final EmployeeRepository employeeRepository;
	private final StatusRepository statusRepository;
	private final BranchService branchService;

	public EmployeeControllerAdmin(EmployeeService employeeService, StatusRepository statusRepository, EmployeeRepository employeeRepository, BranchService branchService) {
		this.employeeService = employeeService;
		this.statusRepository = statusRepository;
		this.employeeRepository = employeeRepository;
		this.branchService = branchService;
	}

	@GetMapping
	public String listEmployees(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployeesAdmin());
		return "employee/list-admin";
	}

	@GetMapping("/fired")
	public String listEmployeesFired(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("employees", employeeService.getAllFiredEmployees(ldto.getBranch_id()));
		return "employee/list-fired";
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		bindAvilableData(model);
		model.addAttribute("employeeDto", new EmployeeEntryDto());
		return "employee/create-admin";
	}

	@PostMapping("/add")
	public String addEmployee(@Valid @ModelAttribute("employeeDto") EmployeeEntryDto dto, BindingResult result, Model model) {
		if (dto.getDob() == null || dto.getDob().isAfter(LocalDate.now().minusYears(16))) {
		    model.addAttribute("error", "The person you are hiring must be older than 16 years.");
		    bindAvilableData(model);
			return "employee/create-admin";
		}
		if (result.hasErrors()) {
			bindAvilableData(model);
			return "employee/create-admin";
		}
		employeeService.createEmployee(dto);
		return "redirect:/admin/employee";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model) {
		System.out.println("Password - " + employeeService.getEmployeeByIdAdmin(id).getPassword());
		model.addAttribute("employeeDto", employeeService.getEmployeeByIdAdmin(id));
		bindAvilableData(model);
		return "employee/edit-admin";
	}
	
	

	@PostMapping("/edit")
	public String updateEmployee(@Valid @ModelAttribute("employeeDto") EmployeeEntryDto dto, BindingResult result,Model model) {
		if (dto.getDob() == null || dto.getDob().isAfter(LocalDate.now().minusYears(16))) {
		    model.addAttribute("error", "The person you are hiring must be older than 16 years.");
		    bindAvilableData(model);
			return "employee/create-admin";
		}
		if (result.hasErrors()) {
			bindAvilableData(model);
			return "employee/edit-admin";
		}
		employeeService.updateEmployee(dto);
		return "redirect:/admin/employee";
	}
	
	@GetMapping("/setnormal/{id}")
	public String setNormal(@PathVariable("id") String id) {
		employeeService.putEmployeeNormal(id);
		return "redirect:/admin/employee";
	}
	
	@GetMapping("/setonleave/{id}")
	public String setOnLeave(@PathVariable("id") String id) {
		employeeService.putEmployeeOnLeave(id);
		return "redirect:/admin/employee";
	}
	
	@GetMapping("/fire/{id}")
	public String fireEmployee(@PathVariable("id") String id) {
		employeeService.fireEmployee(id);
		return "redirect:/admin/employee";
	}
	
	@GetMapping("/transfer/{id}") 
	public String transferEmployee(@PathVariable("id") String id, Model model) {
		model.addAttribute("employeeDto", employeeService.getEmployeeByIdAdmin(id));
		model.addAttribute("branch_name", branchService.findById(employeeService.getEmployeeByIdAdmin(id).getBranch_id()).getName());
		model.addAttribute("branches", branchService.findAll());
		return "employee/transfer-admin";
	}
	
	@PostMapping("/transfer")
	public String transferEmployee(@ModelAttribute("employeeDto") EmployeeEntryDto dto){
		System.out.println("Employee_id - " + dto.getEmployee_id() + " Branch_id - " + dto.getBranch_id());
		employeeService.tranferEmployee(dto.getEmployee_id(), dto.getBranch_id());
		return "redirect:/admin/employee";
	}
	
	private void bindAvilableData(Model model) {
		List<StatusDto> list2 = new ArrayList<StatusDto>();
		for(StatusDto dto2 : statusRepository.findAll2("employee")) {
			if (!dto2.getName().equals("FIRED")) {
				list2.add(dto2);
			}
		}
		
		model.addAttribute("statuses", list2);
		model.addAttribute("gender", Gender.values());
		model.addAttribute("roles", employeeRepository.getAllRoles());
		model.addAttribute("branches", branchService.findAll());
	}
}