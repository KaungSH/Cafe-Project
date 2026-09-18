package cafe.project.YinminThiriSoe.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cafe.project.YinminThiriSoe.repositories.EmployeeRepository;
import cafe.project.YinminThiriSoe.repositories.entities.Employee;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(String id) {
		return employeeRepository.findById(id);
	}

	public Employee login(String email, String password) {
		Employee employee = employeeRepository.findByEmail(email);
		if (employee != null && employee.getPassword().equals(password)) {
			return employee;
		}
		return null;
	}

	public void createEmployee(Employee employee) {
		if (employee.getEmployeeId() == null || employee.getEmployeeId().trim().isEmpty()) {
			employee.setEmployeeId(UUID.randomUUID().toString());
		}
		if (employee.getCreatedAt() == null) {
			employee.setCreatedAt(LocalDateTime.now());
		}
		employeeRepository.add(employee);
	}

	public void updateEmployee(Employee employee) {
		employeeRepository.updateEmployee(employee);
	}

	public void deleteEmployee(String id) {
		employeeRepository.delete(id);
	}
}