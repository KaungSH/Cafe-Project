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
import cafe.project.models.RoleDto;
import cafe.project.models.StatusDto;
import cafe.project.repositories.EmployeeRepository;
import cafe.project.repositories.StatusRepository;
import cafe.project.services.BranchService;
import cafe.project.services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manager-only/employee")
public class EmployeeControllerManager {

	private final EmployeeService employeeService;
	private final EmployeeRepository employeeRepository;
	private final StatusRepository statusRepository;
	private final BranchService branchService;

	public EmployeeControllerManager(EmployeeService employeeService, StatusRepository statusRepository, EmployeeRepository employeeRepository, BranchService branchService) {
		this.employeeService = employeeService;
		this.statusRepository = statusRepository;
		this.employeeRepository = employeeRepository;
		this.branchService = branchService;
	}

	@GetMapping
	public String listEmployees(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("branch_name", branchService.findById(ldto.getBranch_id()).getName());
		model.addAttribute("employees", employeeService.getAllEmployees(ldto.getBranch_id()));
		return "employee/list-manager";
	}

	@GetMapping("/fired")
	public String listEmployeesFired(Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("employees", employeeService.getAllFiredEmployees(ldto.getBranch_id()));
		return "employee/list-fired";
	}

	@GetMapping("/add")
	public String showAddForm(Model model, HttpSession session) {
		EmployeeEntryDto dto = new EmployeeEntryDto();
		dto.setEmployee_role_id(bindAvilableData(model).getRole_id());
		model.addAttribute("employeeDto", dto);
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		model.addAttribute("branch_name", branchService.findById(ldto.getBranch_id()));
		return "employee/create-manager";
	}

	@PostMapping("/add")
	public String addEmployee(@Valid @ModelAttribute("employeeDto") EmployeeEntryDto dto, BindingResult result, Model model, HttpSession session) {
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		if (dto.getDob() == null || dto.getDob().isAfter(LocalDate.now().minusYears(16))) {
		    model.addAttribute("error", "The person you are hiring must be older than 16 years.");
		    bindAvilableData(model);
			return "employee/create-manager";
		}
		if (result.hasErrors()) {
			bindAvilableData(model);
			return "employee/create-manager";
		}
		dto.setBranch_id(ldto.getBranch_id());
		employeeService.createEmployee(dto);
		return "redirect:/manager-only/employee";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("employeeDto", employeeService.getEmployeeById(id));
		bindAvilableData(model);
		return "employee/edit-manager";
	}
	
	

	@PostMapping("/edit")
	public String updateEmployee(@Valid @ModelAttribute("employeeDto") EmployeeEntryDto dto, BindingResult result,Model model) {
		if (dto.getDob() == null || dto.getDob().isAfter(LocalDate.now().minusYears(16))) {
		    model.addAttribute("error", "The person you are hiring must be older than 16 years.");
		    bindAvilableData(model);
			return "employee/edit-manager";
		}
		if (result.hasErrors()) {
			bindAvilableData(model);
			return "employee/edit-manager";
		}
		employeeService.updateEmployee(dto);
		return "redirect:/manager-only/employee";
	}
	
	@GetMapping("/setnormal/{id}")
	public String setNormal(@PathVariable("id") String id) {
		employeeService.putEmployeeNormal(id);
		return "redirect:/manager-only/employee";
	}
	
	@GetMapping("/setonleave/{id}")
	public String setOnLeave(@PathVariable("id") String id) {
		employeeService.putEmployeeOnLeave(id);
		return "redirect:/manager-only/employee";
	}
	
	@GetMapping("/fire/{id}")
	public String fireEmployee(@PathVariable("id") String id) {
		employeeService.fireEmployee(id);
		return "redirect:/manager-only/employee";
	}
	
	@GetMapping("/transfer/{id}") 
	public String transferEmployee(@PathVariable("id") String id, Model model) {
		model.addAttribute("employeeDto", employeeService.getEmployeeById(id));
		model.addAttribute("branch_name", branchService.findById(employeeService.getEmployeeById(id).getBranch_id()).getName());
		model.addAttribute("branches", branchService.findAll());
		return "employee/transfer-manager";
	}
	
	@PostMapping("/transfer")
	public String transferEmployee(@ModelAttribute("employeeDto") EmployeeEntryDto dto){
		System.out.println("Employee_id - " + dto.getEmployee_id() + " Branch_id - " + dto.getBranch_id());
		employeeService.tranferEmployee(dto.getEmployee_id(), dto.getBranch_id());
		return "redirect:/manager-only/employee";
	}
	
	private RoleDto bindAvilableData(Model model) {
		List<RoleDto> list = new ArrayList<RoleDto>();
		for(RoleDto dto : employeeRepository.getAllRoles()) {
			if (dto.getName().equals("STAFF")) {
				list.add(dto);
			}
		}
		
		List<StatusDto> list2 = new ArrayList<StatusDto>();
		for(StatusDto dto2 : statusRepository.findAll2("employee")) {
			if (!dto2.getName().equals("FIRED")) {
				list2.add(dto2);
			}
		}
		model.addAttribute("statuses", list2);
		model.addAttribute("gender", Gender.values());
		return list.getFirst();
	}
}