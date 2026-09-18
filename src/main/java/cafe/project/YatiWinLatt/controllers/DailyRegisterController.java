package cafe.project.YatiWinLatt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cafe.project.YatiWinLatt.models.DailyRegisterEntryDto;
import cafe.project.YatiWinLatt.service.DailyRegisterService;
import cafe.project.YinminThiriSoe.services.EmployeeService;
import cafe.project.YatiWinLatt.service.BranchService;
import cafe.project.YatiWinLatt.repositories.StatusRepository;


@Controller
@RequestMapping("/manager/daily-registers")
public class DailyRegisterController {

  private final DailyRegisterService service;
  private final BranchService branchService;
  private final EmployeeService employeeService;
  private final StatusRepository statusRepository;

  public DailyRegisterController(
      DailyRegisterService service,
      BranchService branchService,
      EmployeeService employeeService,
      StatusRepository statusRepository) {
	  
    this.service = service;
    this.branchService = branchService;
    this.employeeService = employeeService;
    this.statusRepository = statusRepository;
  }

  @GetMapping
  public String showListPage(Model model) {
    model.addAttribute("registers", service.getAllRegisters());
    return "YatiWinLatt/manager/daily_registers/list";
  }

  @GetMapping("/create")
  public String showCreatePage(Model model) {
	System.out.println(statusRepository.findAll("register").get(0));
    model.addAttribute("registerDto", new DailyRegisterEntryDto());
    model.addAttribute("branches", branchService.findAll());
    model.addAttribute("employees", employeeService.getAllEmployees());
    model.addAttribute("statuses", statusRepository.findAll("register"));

    return "YatiWinLatt/manager/daily_registers/create";
  }

  @PostMapping("/save")
  public String saveRegister(@ModelAttribute("registerDto") DailyRegisterEntryDto dto) {
    service.saveRegister(dto);
    return "redirect:/manager/daily-registers";
  }

  @GetMapping("/edit/{register_id}")
  public String showEditPage(@PathVariable("register_id") String register_id, Model model) {
	    System.out.println("Register id = " + register_id);
    DailyRegisterEntryDto dto = service.getRegisterEntryDtoById(register_id);

    model.addAttribute("registerDto", dto);

    model.addAttribute("branches", branchService.findAll());
    model.addAttribute("employees", employeeService.getAllEmployees());
    model.addAttribute("statuses", statusRepository.findAll("register"));

    return "YatiWinLatt/manager/daily_registers/edit";
  }

  @PostMapping("/update")
  public String updateRegister(@ModelAttribute("registerDto") DailyRegisterEntryDto dto) {
    service.updateRegister(dto);
    return "redirect:/manager/daily-registers";
  }

  @GetMapping("/delete/{register_id}")
  public String deleteRegister(@PathVariable("register_id") String register_id) {
    service.deleteRegister(register_id);
    return "redirect:/manager/daily-registers";
  }
}