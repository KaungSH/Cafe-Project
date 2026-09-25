package cafe.project.repositories.mappers;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.models.EmployeeListDto;
import cafe.project.repositories.entities.Employee;

public class EmployeeMapper {

	public static RowMapper<Employee> entityMapper = (rs, rowNum) -> {
		Employee emp = new Employee();
		emp.setEmployee_id(rs.getString("employee_id"));
		emp.setName(rs.getString("name"));
		emp.setEmail(rs.getString("email"));
		emp.setPhotopath(rs.getString("photopath"));
		emp.setPassword(rs.getString("password"));
		emp.setEmployee_status_id(rs.getString("employee_status_id"));
		emp.setPhone(rs.getString("phone"));
		emp.setSalary(rs.getBigDecimal("salary"));
		emp.setAddress(rs.getString("address"));
		emp.setDob(rs.getDate("dob") != null ? rs.getDate("dob").toLocalDate() : null);
		emp.setGender_id(rs.getString("gender_id"));
		emp.setEmployee_role_id(rs.getString("employee_role_id"));
		emp.setBranch_id(rs.getString("branch_id"));
		emp.setCreated_at(
				rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
		return emp;
	};

	public static RowMapper<EmployeeListDto> listDtoMapper = (rs, rowNum) -> {
		EmployeeListDto dto = new EmployeeListDto();
		dto.setEmployee_id(rs.getString("employee_id"));
		dto.setName(rs.getString("name"));
		dto.setEmail(rs.getString("email"));
		dto.setPhone(rs.getString("phone"));
		dto.setSalary(rs.getBigDecimal("salary"));
		dto.setAddress(rs.getString("address"));
		dto.setDob(rs.getDate("dob") != null ? rs.getDate("dob").toLocalDate() : null);
		dto.setPhotopath(rs.getString("photopath"));
		dto.setESname(rs.getString("status_name"));
		dto.setGender_name(rs.getString("gender_name"));
		dto.setRole_name(rs.getString("role_name"));
		dto.setBranch_name(rs.getString("branch_name"));
		return dto;
	};
}