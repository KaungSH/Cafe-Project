package cafe.project.employeemanagement.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.employeemanagement.mappers.LoginMapper;
import cafe.project.employeemanagement.mappers.LoginMapper2;
import cafe.project.employeemanagement.models.ChangePasswordDto;
import cafe.project.employeemanagement.models.ChangeProfileDto;
import cafe.project.employeemanagement.models.LoginDto;

@Repository
public class EmployeeManagementRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public EmployeeManagementRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public LoginDto findById(String id) {
		String sql = "SELECT * FROM employees WHERE employee_id = ?";
		List<LoginDto> entities = jdbcTemplate.query(sql, new LoginMapper(), id);
		return entities.isEmpty()?null:entities.get(0);
	}
	
	public LoginDto findByEmail(String email) {
		String sql = "SELECT * FROM employees WHERE email = ?";
		List<LoginDto> entities = jdbcTemplate.query(sql, new LoginMapper(), email);
		return entities.isEmpty()?null:entities.get(0);
	}
	
	public LoginDto findByLogin(String email, String password) {
		String sql = "SELECT e.*, r.name role_name, r.role_id role_id, b.name branch_name, b.branch_id branch_id, s.name status_name, s.employee_status_id employee_status_id FROM employees e LEFT JOIN employee_roles r ON e.employee_role_id = r.role_id LEFT JOIN employee_statuses s ON e.employee_status_id = s.employee_status_id LEFT JOIN branches b ON e.branch_id = b.branch_id WHERE e.email = ? AND e.password = ?";
		List<LoginDto> entities = jdbcTemplate.query(sql, new LoginMapper2(), email, password);
		return entities.isEmpty()?null:entities.get(0);
	}
	
	public int changePassword(ChangePasswordDto cpdto) {
		String sql = "UPDATE employees SET password = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, cpdto.getNewPassword(), cpdto.getEmployee_id());
	}

	public int changeProfile(ChangeProfileDto cfdto) {
		String sql = "UPDATE employees SET name = ?, phone = ?, address = ?, photopath = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, cfdto.getEmployee_name(), cfdto.getPhone(), cfdto.getAddress(), cfdto.getPhotopath(), cfdto.getEmployee_id());
	}
	
	public int changeStatus(String employee_id, String employee_status_id) {
		String sql = "UPDATE employees SET employee_status_id = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, employee_status_id, employee_id);
	}

}
