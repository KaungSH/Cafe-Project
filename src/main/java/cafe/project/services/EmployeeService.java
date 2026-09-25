package cafe.project.services;

import java.util.List;
import org.springframework.stereotype.Service;

import cafe.project.employeemanagement.models.ChangePasswordDto;
import cafe.project.employeemanagement.models.ChangeProfileDto;
import cafe.project.employeemanagement.services.PasswordService;
import cafe.project.models.EmployeeEntryDto;
import cafe.project.models.EmployeeListDto;
import cafe.project.repositories.EmployeeRepository;
import cafe.project.repositories.entities.Employee;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final PasswordService pws;

	public EmployeeService(EmployeeRepository employeeRepository, PasswordService pws) {
		this.employeeRepository = employeeRepository;
		this.pws = pws;
	}

	public Employee login(String email, String password) {
		Employee employee = employeeRepository.findByEmail(email);
		if (employee != null && pws.matches(password, employee.getPassword())) {
			return employee;
		}
		return null;
	}

	public List<EmployeeListDto> getAllEmployeeListDto() {
		return employeeRepository.findAllEmployeeListDto();
	}

	public Employee getEmployeeById(String id) {
		return employeeRepository.findById(id);
	}

	public void createEmployee(EmployeeEntryDto dto) {
		if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
			dto.setPassword(pws.encode(dto.getPassword()));
		}
		employeeRepository.add(dto);
	}

	public void updateEmployee(EmployeeEntryDto dto) {
		employeeRepository.updateEmployee(dto);
	}

	public void deleteEmployee(String id) {
		employeeRepository.delete(id);
	}

	public int changePassword(ChangePasswordDto cpdto) {
		Employee employee = employeeRepository.findById(cpdto.getEmployee_id());
		if (employee == null)
			return 0;

		return pws.matches(cpdto.getOldPassword(), employee.getPassword())
				? employeeRepository.changePassword(cpdto.getEmployee_id(), pws.encode(cpdto.getNewPassword()))
				: 0;
	}

	public int changeStatus(String id, String statusId) {
		if (employeeRepository.findById(id) == null) {
			return 0;
		}
		return employeeRepository.changeStatus(id, statusId);
	}

	public int changeProfile(ChangeProfileDto cfdto) {
		Employee employee = employeeRepository.findById(cfdto.getEmployee_id());
		if (employee == null)
			return 0;

		return pws.matches(cfdto.getPassword(), employee.getPassword())
				? employeeRepository.changeProfile(cfdto.getEmployee_id(), cfdto.getEmployee_name(), cfdto.getPhone(),
						cfdto.getAddress(), cfdto.getPhotopath())
				: 0;
	}
}