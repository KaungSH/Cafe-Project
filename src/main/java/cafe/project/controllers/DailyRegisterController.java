package cafe.project.controllers;

import java.sql.Time;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cafe.project.YatiWinLatt.models.DailyRegisterEntryDto;
import cafe.project.YatiWinLatt.service.DailyRegisterService;
import cafe.project.services.EmployeeService;
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
    model.addAttribute("employees", employeeService.getAllEmployeeListDto());
    model.addAttribute("statuses", statusRepository.findAll("register"));

    return "YatiWinLatt/manager/daily_registers/create";
  }
  
  @PostMapping("/save")
  public String saveRegister(@ModelAttribute("registerDto") DailyRegisterEntryDto dto) {
    service.saveRegister(dto);
    return "redirect:/manager/daily-registers";
  }
  
  @GetMapping("/openshift")
  public String openShift(Model model) {
	System.out.println(statusRepository.findAll("register").get(0));
	DailyRegisterEntryDto dto = new DailyRegisterEntryDto();
	dto.setDate(LocalDate.now());
	dto.setOpened_at(Time.valueOf(java.time.LocalTime.now()));
    model.addAttribute("registerDto", dto);
    model.addAttribute("branches", branchService.findAll());
    model.addAttribute("employees", employeeService.getAllEmployeeListDto());
    model.addAttribute("statuses", statusRepository.findAll("register"));

    return "YatiWinLatt/manager/daily_registers/openshift";
  }

  @PostMapping("/openshift")
  public String openShift(@ModelAttribute("registerDto") DailyRegisterEntryDto dto) {
	  //Place Holder Time
	dto.setClosed_at(dto.getOpened_at());
    service.saveRegister(dto);
    return "redirect:/manager/daily-registers";
  }
  
  @GetMapping("/closeshift")
  public String closeShift(Model model) {
	System.out.println(statusRepository.findAll("register").get(0));
	DailyRegisterEntryDto dto = service.getRegisterEntryDtoByDate(LocalDate.now());
	dto.setClosed_at(Time.valueOf(java.time.LocalTime.now()));
    model.addAttribute("registerDto", dto);
    model.addAttribute("branches", branchService.findAll());
    model.addAttribute("employees", employeeService.getAllEmployeeListDto());
    model.addAttribute("statuses", statusRepository.findAll("register"));

    return "YatiWinLatt/manager/daily_registers/closeshift";
  }

  @PostMapping("/closeshift")
  public String closeShift(@ModelAttribute("registerDto") DailyRegisterEntryDto dto) {
    service.updateRegister(dto);
    return "redirect:/manager/WasteLogs/today-expired";
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

    return "YatiWinLatt/manager/daily_registers/edit";
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
    return "redirect:/manager/daily-registers";
  }
}