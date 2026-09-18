package cafe.project.YinminThiriSoe.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.YinminThiriSoe.repositories.entities.Employee;

public class EmployeeMapper implements RowMapper<Employee> {
	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setEmployeeId(rs.getString("employee_id"));
		employee.setName(rs.getString("name"));
		employee.setEmail(rs.getString("email"));
		employee.setPhotoPath(rs.getString("photopath"));
		employee.setPassword(rs.getString("password"));
		employee.setEmployeeStatusId(rs.getString("employee_status_id"));
		employee.setPhone(rs.getString("phone"));
		employee.setSalary(rs.getDouble("salary"));
		employee.setAddress(rs.getString("address"));
		employee.setDob(rs.getObject("dob", LocalDate.class));
		employee.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));

		employee.setGenderId(rs.getString("gender_id"));
		employee.setEmployeeRoleId(rs.getString("employee_role_id"));
		employee.setBranchId(rs.getString("branch_id"));

		return employee;
	}
}
