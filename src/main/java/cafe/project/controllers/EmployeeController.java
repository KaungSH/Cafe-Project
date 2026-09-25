package cafe.project.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cafe.project.employeemanagement.models.ChangePasswordDto;
import cafe.project.employeemanagement.models.ChangeProfileDto;
import cafe.project.models.EmployeeEntryDto;
import cafe.project.services.EmployeeService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/manager/employee")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping
  public String listEmployees(Model model) {
    model.addAttribute("employees", employeeService.getAllEmployeeListDto());
    return "employee/list";
  }

  @GetMapping("/add")
  public String showAddForm(Model model) {
    model.addAttribute("employeeDto", new EmployeeEntryDto());
    return "employee/form";
  }

  @PostMapping("/add")
  public String addEmployee(@Valid @ModelAttribute("employeeDto") EmployeeEntryDto dto, BindingResult result) {
    if (result.hasErrors()) {
      return "employee/form";
    }
    employeeService.createEmployee(dto);
    return "redirect:/manager/employee";
  }

  @GetMapping("/delete/{employee_id}")
  public String deleteEmployee(@PathVariable("employee_id") String employee_id) {
    employeeService.deleteEmployee(employee_id);
    return "redirect:/manager/employee";
  }

  @GetMapping("/changepassword")
  @ResponseBody
  public int changePassword(@RequestHeader("id") String id, 
                            @RequestHeader("oldPassword") String oldPassword, 
                            @RequestHeader("newPassword") String newPassword) {
    ChangePasswordDto dto = new ChangePasswordDto();
    dto.setEmployee_id(id);
    dto.setOldPassword(oldPassword);
    dto.setNewPassword(newPassword);

    return this.employeeService.changePassword(dto);
  }

  @GetMapping("/changestatus")
  @ResponseBody
  public int changeStatus(@RequestHeader("id") String id, @RequestHeader("status") String status) {
    return this.employeeService.changeStatus(id, status);
  }

  @PostMapping("/changeprofile")
  @ResponseBody
  public int changeProfile(@ModelAttribute ChangeProfileDto cfdto) {
    return this.employeeService.changeProfile(cfdto);
  }
}