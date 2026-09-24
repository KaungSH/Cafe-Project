package cafe.project.controllers;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cafe.project.services.BranchService;
import cafe.project.services.DailyRegisterService;
import cafe.project.services.EmployeeService;

import cafe.project.models.DailyRegisterEntryDto;
import cafe.project.repositories.StatusRepository;

@Controller
@RequestMapping("/manager/daily-registers")
public class DailyRegisterController {

  private final DailyRegisterService service;
  private final BranchService branchService;
  private final EmployeeService employeeService;
  private final StatusRepository statusRepository;

  public DailyRegisterController(DailyRegisterService service, BranchService branchService, EmployeeService employeeService, StatusRepository statusRepository) {

    this.service = service;
    this.branchService = branchService;
    this.employeeService = employeeService;
    this.statusRepository = statusRepository;
  }
  
  @GetMapping("/{sort}")
  public String showListPageAll(@PathVariable("sort") String sort, Model model) {
    if(sort.equals("closed")) {
      model.addAttribute("registers", service.getAllClosed());
      return "daily_registers/list-all";
    }
    if(sort.equals("opened")) {
      model.addAttribute("registers", service.getAllOpened());
      return "daily_registers/list-all";
    }
    model.addAttribute("registers", service.getAllRegisters());
    return "daily_registers/list-all";
  }
  
  @GetMapping("/deleted")
  public String showListPageDeleted(Model model) {
    model.addAttribute("registers", service.getAllDeleted());
    return "daily_registers/list-deleted";
  }
  

  @GetMapping("/edit/{register_id}")
  public String showEditPage(@PathVariable("register_id") String register_id, Model model) {
    System.out.println("Register id = " + register_id);
    System.out.println("Register date = " + service.getRegisterEntryDtoById(register_id).getDate());
    DailyRegisterEntryDto dto = service.getRegisterEntryDtoById(register_id);

    model.addAttribute("registerDto", dto);

    model.addAttribute("branches", branchService.findAll());
    model.addAttribute("employees", employeeService.getAllEmployeeListDto());
    model.addAttribute("statuses", statusRepository.findAll("register"));

    return "daily_registers/edit";
  }

  @PostMapping("/update")
  public String updateRegister(@ModelAttribute("registerDto") DailyRegisterEntryDto dto) {
    System.out.println("Register date DTO = " + dto.getDate());
    service.updateRegister(dto);
    return "redirect:/manager/daily-registers";
  }

  @GetMapping("/delete/{register_id}")
  public String deleteRegister(@PathVariable("register_id") String register_id) {
    service.deleteRegister(register_id);
    return "redirect:/manager/daily-registers/all";
  }
  
  @GetMapping("/recover/{register_id}")
  public String recoverRegister(@PathVariable("register_id") String register_id) {
    service.recoverRegister(register_id);
    return "redirect:/manager/daily-registers/deleted";
  }
}