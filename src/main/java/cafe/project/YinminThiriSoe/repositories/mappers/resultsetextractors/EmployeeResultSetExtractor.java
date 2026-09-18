package cafe.project.YinminThiriSoe.repositories.mappers.resultsetextractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.YinminThiriSoe.repositories.entities.Employee;

public class EmployeeResultSetExtractor implements ResultSetExtractor<List<Employee>> {

	@Override
	public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, Employee> employeeMap = new LinkedHashMap<>();
		while (rs.next()) {
			String employeeId = rs.getString("e.employee_id");
			Employee employee = employeeMap.get(employeeId);
			if (employee == null) {
				employee = new Employee();
				employee.setEmployeeId(rs.getString("e.employee_id"));
				employee.setName(rs.getString("e.name")); ;
				employee.setEmail(rs.getString("e.email"));
				employee.setPhotoPath(rs.getString("e.photopath"));
				employee.setPassword(rs.getString("e.password"));	;
				employee.setEmployeeStatusId(rs.getString("e.employee_status_id"));
				employee.setPhone(rs.getString("e.phone"));
				employee.setSalary(rs.getDouble("e.salary"));
				employee.setAddress(rs.getString("e.address"));
				employee.setDob(rs.getObject("e.dob", LocalDate.class));
				employee.setGenderId(rs.getString("e.gender"));
				employee.setEmployeeRoleId(rs.getString("e.employee_role_id"));
				employee.setBranchId(rs.getString("e.branch_id"));	
				employee.setCreatedAt(rs.getObject("e.created_at", LocalDateTime.class));
				employee.setEmployee_branch_name(rs.getString("b.name"));
				employee.setEmployee_role_name(rs.getString("er.name"));
				employee.setGender_name(rs.getString("g.gender_name"));
				employee.setEmployee_status_name(rs.getString("es.name"));
				
			}
			
			employeeMap.put(employeeId, employee);

			
		}
		
		return new ArrayList<>(employeeMap.values());
	}

}
