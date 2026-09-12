package cafe.project.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.repository.entity.Employee;

public class EmployeeResultSetExtractor implements ResultSetExtractor<List<Employee>> {

	@Override
	public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, Employee> employeeMap = new LinkedHashMap<>();
		while (rs.next()) {
			String employeeId = rs.getString("e.employee_id");
			Employee employee = employeeMap.get(employeeId);
			if (employee == null) {
				employee = new Employee();
				rs.getString("e.employee_id");
				rs.getString("e.name");
				rs.getString("e.email");
				rs.getString("e.photopath");
				rs.getString("e.password");
				rs.getString("e.employee_status_id");
				rs.getString("e.phone");
				rs.getDouble("e.salary");
				rs.getString("e.address");
				rs.getObject("e.dob", LocalDate.class);
				rs.getString("e.gender_id");
				rs.getString("e.employee_role_id");
				rs.getString("e.branch_id");
				rs.getObject("e.created_at", LocalDate.class);

			}

			return new ArrayList<>(employeeMap.values());
		}
		return null;
	}

}
