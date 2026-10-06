package cafe.project.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.models.EmployeeEntryDto;
import cafe.project.models.RoleDto;
import cafe.project.repositories.entities.Employee;
import cafe.project.repositories.mappers.EmployeeMapper;
import cafe.project.repositories.mappers.EmployeeRoleMapper;
import cafe.project.repositories.mappers.RoleMapper;

@Repository
public class EmployeeRepository {

	private final JdbcTemplate jdbcTemplate;

	public EmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Employee> findAllAdmin() {
		String sql = "SELECT e.* FROM employees e LEFT JOIN employee_statuses es ON e.employee_status_id = es.employee_status_id LEFT JOIN employee_roles er ON e.employee_role_id = er.role_id WHERE es.name != 'FIRED' AND er.name != 'ADMIN'";
		return this.jdbcTemplate.query(sql, new EmployeeMapper());
	}
	
	public List<Employee> findAllAdminFired() {
		String sql = "SELECT e.* FROM employees e LEFT JOIN employee_statuses es ON e.employee_status_id = es.employee_status_id LEFT JOIN employee_roles er ON e.employee_role_id = er.role_id WHERE es.name = 'FIRED' AND er.name != 'ADMIN'";
		return this.jdbcTemplate.query(sql, new EmployeeMapper());
	}
	
	public List<Employee> findAllAdminManagers() {
		String sql = "SELECT e.* FROM employees e LEFT JOIN employee_statuses es ON e.employee_status_id = es.employee_status_id LEFT JOIN employee_roles er ON e.employee_role_id = er.role_id WHERE es.name != 'FIRED' AND er.name = 'MANAGER'";
		return this.jdbcTemplate.query(sql, new EmployeeMapper());
	}
	
	public Employee findByIdAdmin(String employeeId) {
		String sql = "SELECT * FROM employees WHERE employee_id = ?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeMapper(), employeeId);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public List<Employee> findAll(String branch_id) {
		String sql = "SELECT e.* FROM employees e LEFT JOIN employee_statuses es ON e.employee_status_id = es.employee_status_id LEFT JOIN employee_roles er ON e.employee_role_id = er.role_id WHERE es.name != 'FIRED' AND er.name != 'ADMIN' AND er.name != 'MANAGER' AND e.branch_id = ?";
		return this.jdbcTemplate.query(sql, new EmployeeMapper(), branch_id);
	}
	
	public List<Employee> findAllFired(String branch_id) {
		String sql = "SELECT e.* FROM employees e LEFT JOIN employee_statuses es ON e.employee_status_id = es.employee_status_id LEFT JOIN employee_roles er ON e.employee_role_id = er.role_id WHERE es.name = 'FIRED' AND er.name != 'ADMIN' AND er.name != 'MANAGER' AND e.branch_id = ?";
		return this.jdbcTemplate.query(sql, new EmployeeMapper(), branch_id);
	}

	public Employee findById(String employeeId) {
		String sql = "SELECT e.* FROM employees e LEFT JOIN employee_roles er ON e.employee_role_id = er.role_id WHERE employee_id = ? AND er.name != 'ADMIN' AND er.name != 'MANAGER'";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeMapper(), employeeId);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int add(EmployeeEntryDto dto) {
		String sql = "INSERT INTO employees (employee_id, name, email, password, photopath, phone, salary, address, dob, gender, employee_status_id, employee_role_id, branch_id, created_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, UUID.randomUUID().toString(), dto.getName(), dto.getEmail(), dto.getPassword(), dto.getPhotopath(), dto.getPhone(), dto.getSalary(), dto.getAddress(), dto.getDob(), dto.getGender().toString(), dto.getEmployee_status_id(), dto.getEmployee_role_id(), dto.getBranch_id(), LocalDateTime.now());
	}

	public int updateEmployee(EmployeeEntryDto dto) {
		String sql = "UPDATE employees SET name = ?, email = ?, phone = ?, salary = ?, address = ?, dob = ?, gender = ?, employee_status_id = ?, employee_role_id = ?, branch_id = ?, photopath = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, dto.getName(), dto.getEmail(), dto.getPhone(), dto.getSalary(), dto.getAddress(), dto.getDob(), dto.getGender().toString(), dto.getEmployee_status_id(), dto.getEmployee_role_id(), dto.getBranch_id(), dto.getPhotopath(), dto.getEmployee_id());
	}
	
	public int changeStatus(String employee_id, String status_id) {
		String sql = "UPDATE employees SET employee_status_id = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, status_id, employee_id);
	}
	
	public int changeBranches(String employee_id, String branch_id) {
		String sql = "UPDATE employees SET branch_id = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, branch_id, employee_id);
	}
	
	public String getRoleName(String roleId) {
		List<String> entities = jdbcTemplate.query("SELECT * FROM employee_roles WHERE role_id = ?", new EmployeeRoleMapper(), roleId);
		return entities.isEmpty() ? null : entities.get(0);
	}
	
	public List<RoleDto> getAllRoles() {
		return jdbcTemplate.query("SELECT * FROM employee_roles", new RoleMapper());
	}
}