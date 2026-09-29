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
import org.springframework.web.bind.annotation.RequestParam;
import cafe.project.employeemanagement.models.ChangePasswordDto;
import cafe.project.employeemanagement.models.ChangeProfileDto;
import cafe.project.models.EmployeeEntryDto;
import cafe.project.repositories.entities.Employee;
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

 
  @GetMapping("/deleted-list")
  public String deletedList(Model model) {
    model.addAttribute("employees", employeeService.getDeletedEmployeeListDto());
    return "employee/deleted-list";
  }

  
  @GetMapping("/filter")
  public String filterEmployees(@RequestParam(value = "employee_role_id", required = false) String employee_role_id,
                                @RequestParam(value = "employee_status_id", required = false) String employee_status_id,
                                @RequestParam(value = "branch_id", required = false) String branch_id,
                                Model model) {
    if (employee_role_id != null && !employee_role_id.isEmpty()) {
      model.addAttribute("employees", employeeService.getEmployeesByRole(employee_status_id));
    } else if (employee_status_id != null && !employee_status_id.isEmpty()) {
      model.addAttribute("employees", employeeService.getEmployeesByStatus(employee_status_id));
    } else if (branch_id != null && !branch_id.isEmpty()) {
      model.addAttribute("employees", employeeService.getEmployeesByBranch(branch_id));
    } else {
      model.addAttribute("employees", employeeService.getAllEmployeeListDto());
    }
    return "employee/list";
  }


  @GetMapping("/add")
  public String showAddForm(Model model) {
    model.addAttribute("employeeDto", new EmployeeEntryDto());
    return "employee/create";
  }


  @PostMapping("/add")
  public String addEmployee(@Valid @ModelAttribute("employeeDto") EmployeeEntryDto dto, BindingResult result) {
    if (result.hasErrors()) {
      return "employee/create";
    }
    employeeService.createEmployee(dto);
    return "redirect:/manager/employee";
  }


  @GetMapping("/edit/{id}")
  public String showEditForm(@PathVariable("id") String id, Model model) {
    Employee emp = employeeService.getEmployeeById(id);
    if (emp == null) {
      return "redirect:/manager/employee";
    }
    
    EmployeeEntryDto dto = new EmployeeEntryDto();
    dto.setEmployee_id(emp.getEmployee_id());
    dto.setName(emp.getName());
    dto.setEmail(emp.getEmail());
    dto.setPhone(emp.getPhone());
    dto.setSalary(emp.getSalary());
    dto.setAddress(emp.getAddress());
    dto.setDob(emp.getDob());
    dto.setGender(emp.getGender());
    dto.setEmployee_status_id(emp.getEmployee_status_id());
    dto.setEmployee_role_id(emp.getEmployee_role_id());
    dto.setBranch_id(emp.getBranch_id());
    dto.setPhotopath(emp.getPhotopath());

    model.addAttribute("employeeDto", dto);
    return "employee/edit";
  }


  @PostMapping("/edit")
public String updateEmployee(@Valid @ModelAttribute("employeeDto") EmployeeEntryDto dto, BindingResult result) {
    if (result.hasErrors()) {
      return "employee/edit";
    }
    employeeService.updateEmployee(dto);
    return "redirect:/manager/employee";
  }

  @GetMapping("/delete/{employee_id}")
  public String deleteEmployee(@PathVariable("employee_id") String employee_id) {
    employeeService.softDeleteEmployee(employee_id);
    return "redirect:/manager/employee";
  }


  @GetMapping("/recover/{employee_id}")
  public String recoverEmployee(@PathVariable("employee_id") String employee_id) {
    employeeService.recoverEmployee(employee_id);
    return "redirect:/manager/employee/deleted-list";
  }


  @GetMapping("/hard-delete/{employee_id}")
  public String hardDeleteEmployee(@PathVariable("employee_id") String employee_id) {
    employeeService.hardDeleteEmployee(employee_id);
    return "redirect:/manager/employee/deleted-list";
  }

 
  @GetMapping("/changepassword")

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

  public int changeStatus(@RequestHeader("id") String id, @RequestHeader("status") String status) {
    return this.employeeService.changeStatus(id, status);
  }

  @PostMapping("/changeprofile")

  public int changeProfile(@ModelAttribute ChangeProfileDto cfdto) {
    return this.employeeService.changeProfile(cfdto);
  }
}