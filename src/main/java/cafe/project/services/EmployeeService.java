package cafe.project.services;

import java.util.List;
import org.springframework.stereotype.Service;
import cafe.project.employeemanagement.services.PasswordService;
import cafe.project.models.EmployeeEntryDto;
import cafe.project.models.EmployeeListDto;
import cafe.project.models.Gender;
import cafe.project.models.StatusDto;
import cafe.project.repositories.EmployeeRepository;
import cafe.project.repositories.StatusRepository;
import cafe.project.repositories.entities.Employee;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final PasswordService pws;
	private final StatusRepository statusRepository;
	private final BranchService branchService;

	public EmployeeService(EmployeeRepository employeeRepository, PasswordService pws, BranchService branchService, StatusRepository statusRepository) {
		this.employeeRepository = employeeRepository;
		this.pws = pws;
		this.statusRepository = statusRepository;
		this.branchService = branchService;
	}

	public List<EmployeeListDto> getAllEmployees(String branch_id) {
		return employeeRepository.findAll(branch_id).stream().map(this::toListDto).toList();
	}
	
	public List<EmployeeListDto> getAllFiredEmployees(String branch_id) {
		return employeeRepository.findAllFired(branch_id).stream().map(this::toListDto).toList();
	}
	
	public EmployeeEntryDto getEmployeeById(String employee_id) {
		return (employeeRepository.findById(employee_id) == null) ? null : toEntryDto(employeeRepository.findById(employee_id));
	}
	
	public List<EmployeeListDto> getAllEmployeesAdmin() {
		return employeeRepository.findAllAdmin().stream().map(this::toListDto).toList();
	}
	
	public List<EmployeeListDto> getAllManagerAdmin() {
		return employeeRepository.findAllAdminManagers().stream().map(this::toListDto).toList();
	}
	
	public List<EmployeeListDto> getAllFiredEmployeesAdmin() {
		return employeeRepository.findAllAdminFired().stream().map(this::toListDto).toList();
	}
	
	public EmployeeEntryDto getEmployeeByIdAdmin(String employee_id) {
		return (employeeRepository.findByIdAdmin(employee_id) == null) ? null : toEntryDto(employeeRepository.findByIdAdmin(employee_id));
	}

	public void createEmployee(EmployeeEntryDto dto) {
		if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
			dto.setPassword(pws.encode(dto.getPassword()));
			employeeRepository.add(dto);
		}
	}

	public void updateEmployee(EmployeeEntryDto dto) {
		employeeRepository.updateEmployee(dto);
	}
	
	public void fireEmployee(String employee_id) {
		employeeRepository.changeStatus(employee_id, getStatusId("FIRED"));
	}
	
	public void putEmployeeOnLeave(String employee_id) {
		employeeRepository.changeStatus(employee_id, getStatusId("ONLEAVE"));
	}
	
	public void putEmployeeNormal(String employee_id) {
		employeeRepository.changeStatus(employee_id, getStatusId("NORMAL"));
	}
	
	public void tranferEmployee(String employee_id, String branch_id) {
		employeeRepository.changeBranches(employee_id, branch_id);
	}
	
	private EmployeeListDto toListDto(Employee entity) {
		return new EmployeeListDto(entity.getEmployee_id(), entity.getName(), entity.getEmail(), entity.getPhone(), entity.getSalary(), entity.getAddress(), entity.getDob(), entity.getPhotopath(), Gender.valueOf(entity.getGender()), employeeRepository.getRoleName(entity.getEmployee_role_id()), branchService.findById(entity.getBranch_id()).getName(), statusRepository.findById("employee", entity.getEmployee_status_id()).getName());
	}
	
	private EmployeeEntryDto toEntryDto(Employee entity) {
		return new EmployeeEntryDto(entity.getEmployee_id(), entity.getName(), entity.getEmail(), entity.getPassword(), entity.getPhotopath(), entity.getPhone(), entity.getSalary(), entity.getAddress(), entity.getDob(), Gender.valueOf(entity.getGender()), entity.getEmployee_status_id(), entity.getEmployee_role_id(), entity.getBranch_id());
	}
	
	private String getStatusId(String type) {
		for(StatusDto dto : statusRepository.findAll2("employee")) {
			if(dto.getName().equals(type)) {
				String id2 = dto.getStatus_id();
				System.out.println("Status id - " + id2);
				return id2;
			}
		}
		return null;
	}
}