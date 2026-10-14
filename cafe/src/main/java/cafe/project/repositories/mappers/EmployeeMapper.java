package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import cafe.project.repositories.entities.Employee;

public class EmployeeMapper implements RowMapper<Employee>  {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee emp = new Employee();
		emp.setEmployee_id(rs.getString("employee_id"));
		emp.setName(rs.getString("name"));
		emp.setEmail(rs.getString("email"));
		emp.setPhotopath(rs.getString("photopath"));
		emp.setPassword(rs.getString("password"));
		emp.setEmployee_status_id(rs.getString("employee_status_id"));
		emp.setPhone(rs.getString("phone"));
		emp.setSalary(rs.getDouble("salary"));
		emp.setAddress(rs.getString("address"));
		emp.setDob(rs.getObject("dob", LocalDate.class));
		emp.setGender(rs.getString("gender"));
		emp.setEmployee_role_id(rs.getString("employee_role_id"));
		emp.setBranch_id(rs.getString("branch_id"));
		emp.setCreated_at( rs.getObject("created_at", LocalDateTime.class));
		return emp;
	}
}