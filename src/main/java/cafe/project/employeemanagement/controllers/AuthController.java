package cafe.project.employeemanagement.controllers;

import java.sql.Time;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cafe.project.services.BranchService;
import cafe.project.services.DailyRegisterService;
import cafe.project.services.EmployeeService;
import cafe.project.employeemanagement.models.LoginDto;
import cafe.project.employeemanagement.services.EmployeeManagementService;
import cafe.project.models.DailyRegisterEntryDto;
import cafe.project.repositories.StatusRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	
	private final EmployeeManagementService service;
	private final DailyRegisterService registerService;
	private final StatusRepository statusRepository;
	
	public AuthController(EmployeeManagementService userService, DailyRegisterService registerService, BranchService branchService, EmployeeService employeeService, StatusRepository statusRepository) {
		this.service = userService;
		this.registerService = registerService;
		this.statusRepository = statusRepository;
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new LoginDto());
		return "common/user/login";
	}
	
	@PostMapping("/login")
	public String loginPage(@ModelAttribute("user") LoginDto ldto, HttpSession oldsession, HttpServletRequest request, Model model) {
		LoginDto ldto2 = service.findByLogin(ldto);
		
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
		if(ldto2.getEmployee_role().equals("Admin")) {
			return "redirect:/";
		}
		return "redirect:/openshift";
	}
	
	
	@GetMapping("/logout")
	public String logoutPage(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/openshift")
	public String openShift(Model model, HttpSession session) {
		System.out.println(statusRepository.findAll("register").get(0));
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		
		DailyRegisterEntryDto dto = registerService.getRegisterEntryDtoByDate(LocalDate.now(), ldto.getEmployee_id());
		
		if (dto != null) {
			if(ldto.getEmployee_role().equals("MANAGER")) {
				return "redirect:/manager/daily-registers/all";
			}
			return "redirect:/";
		}
		
		dto = new DailyRegisterEntryDto();
		dto.setDate(LocalDate.now());
		dto.setOpened_at(Time.valueOf(java.time.LocalTime.now()));
		dto.setRegister_status_id(statusRepository.findAll2("register").get(0).getStatus_id());
		dto.setBranch_id(ldto.getBranch_id());
		dto.setEmployee_id(ldto.getEmployee_id());
		model.addAttribute("registerDto", dto);
		model.addAttribute("branch", ldto.getBranch_name());
		model.addAttribute("employee", ldto.getEmployee_name());
		model.addAttribute("status", statusRepository.findAll2("register").get(0).getName());

		return "daily_registers/openshift";
	}

	@PostMapping("/openshift")
	public String openShift(@ModelAttribute("registerDto") DailyRegisterEntryDto dto, HttpSession session) {
		registerService.saveRegister(dto);
		if(((LoginDto) session.getAttribute("loggedInUser")).getEmployee_role().equals("MANAGER")) {
			return "redirect:/manager/daily-registers/all";
		}
		return "redirect:/";
	}

	@GetMapping("/closeshift")
	public String closeShift(Model model, HttpSession session) {
		System.out.println(statusRepository.findAll("register").get(0));
		LoginDto ldto = (LoginDto) session.getAttribute("loggedInUser");
		DailyRegisterEntryDto dto = registerService.getRegisterEntryDtoByDate(LocalDate.now(), ldto.getEmployee_id());
		dto.setClosed_at(Time.valueOf(java.time.LocalTime.now()));
		model.addAttribute("registerDto", dto);
		dto.setRegister_status_id(statusRepository.findAll2("register").get(1).getStatus_id());
		model.addAttribute("branch", ldto.getBranch_name());
		model.addAttribute("employee", ldto.getEmployee_name());
		model.addAttribute("status", statusRepository.findAll2("register").get(1).getName());

		return "daily_registers/closeshift";
	}

	@PostMapping("/closeshift")
	public String closeShift(@ModelAttribute("registerDto") DailyRegisterEntryDto dto, HttpSession session) {
		registerService.updateRegister(dto);
		if(((LoginDto) session.getAttribute("loggedInUser")).getEmployee_role().equals("MANAGER")) {
			return "redirect:/manager/daily-registers/all";
		}
		return "redirect:/";
	}
	
}
