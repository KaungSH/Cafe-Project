package cafe.project.YinminThiriSoe.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.YinminThiriSoe.repositories.entities.Employee;
import cafe.project.YinminThiriSoe.repositories.mappers.EmployeeMapper;
import cafe.project.YinminThiriSoe.repositories.mappers.resultsetextractors.EmployeeResultSetExtractor;

@Repository
public class EmployeeRepository {

	private final JdbcTemplate jdbcTemplate;

	public EmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Employee> findAll() {
		String sql = "SELECT * FROM employees ";
		return this.jdbcTemplate.query(sql, new EmployeeMapper());
	}

	public List<Employee> findAllWithRelation() {
		String sql = "SELECT \r\n" + "    e.*,\r\n" + "    es.name,\r\n" + "    g.gender_id,  \r\n"
				+ "    er.name,    \r\n" + "    b.name    \r\n" + "FROM employees e\r\n"
				+ "LEFT JOIN employee_statuses es \r\n" + "    ON e.employee_status_id = es.employee_status_id\r\n"
				+ "LEFT JOIN genders g \r\n" + "    ON e.gender_id = g.gender_id\r\n"
				+ "LEFT JOIN employee_roles er \r\n" + "    ON e.employee_role_id = er.role_id\r\n"
				+ "LEFT JOIN branches b \r\n" + "    ON e.branch_id = b.branch_id";
		return this.jdbcTemplate.query(sql, new EmployeeResultSetExtractor());
	}

	public List<Employee> findByBranch(String branchId) {
		String sql = "SELECT * FROM employees WHERE branch_id = ?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeMapper(), branchId);
		return entities.isEmpty() ? null : entities;
	}

	public Employee findByBranchWithRelations(String branchId) {
		String sql = "SELECT \r\n" + "    e.*,\r\n" + "    es.name,\r\n" + "    g.gender_id,  \r\n"
				+ "    er.name,    \r\n" + "    b.name    \r\n" + "FROM employees e\r\n"
				+ "LEFT JOIN employee_statuses es \r\n" + "    ON e.employee_status_id = es.employee_status_id\r\n"
				+ "LEFT JOIN genders g \r\n" + "    ON e.gender_id = g.gender_id\r\n"
				+ "LEFT JOIN employee_roles er \r\n" + "    ON e.employee_role_id = er.role_id\r\n"
				+ "LEFT JOIN branches b \r\n" + "    ON e.branch_id = b.branch_id WHERE b.branch_id=?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeResultSetExtractor(), branchId);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public List<Employee> findByRole(String employeeRoleId) {
		String sql = "SELECT * FROM employees WHERE employee_role_id = ?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeMapper(), employeeRoleId);
		return entities.isEmpty() ? null : entities;
	}

	public Employee findByRoleWithRelations(String employeeRoleId) {
		String sql = "SELECT \r\n" + "    e.*,\r\n" + "    es.name,\r\n" + "    g.gender_id,  \r\n"
				+ "    er.name,    \r\n" + "    b.name    \r\n" + "FROM employees e\r\n"
				+ "LEFT JOIN employee_statuses es \r\n" + "    ON e.employee_status_id = es.employee_status_id\r\n"
				+ "LEFT JOIN genders g \r\n" + "    ON e.gender_id = g.gender_id\r\n"
				+ "LEFT JOIN employee_roles er \r\n" + "    ON e.employee_role_id = er.role_id\r\n"
				+ "LEFT JOIN branches b \r\n" + "    ON e.branch_id = b.branch_id WHERE e.employee_role_id=?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeResultSetExtractor(), employeeRoleId);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public List<Employee> findByStatus(String employeeStatusId) {
		String sql = "SELECT * FROM employees WHERE employee_status_id = ?";
		return jdbcTemplate.query(sql, new EmployeeMapper(), employeeStatusId);
	}

	public Employee findById(String employeeId) {
		String sql = "SELECT * FROM employees WHERE employee_id = ?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeMapper(), employeeId);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public Employee findByIdWithRelations(String employeeId) {
		String sql = "SELECT \r\n" + "    e.*,\r\n" + "    es.name,\r\n" + "    g.gender_id,  \r\n"
				+ "    er.name,    \r\n" + "    b.name    \r\n" + "FROM employees e\r\n"
				+ "LEFT JOIN employee_statuses es \r\n" + "    ON e.employee_status_id = es.employee_status_id\r\n"
				+ "LEFT JOIN genders g \r\n" + "    ON e.gender_id = g.gender_id\r\n"
				+ "LEFT JOIN employee_roles er \r\n" + "    ON e.employee_role_id = er.role_id\r\n"
				+ "LEFT JOIN branches b \r\n" + "    ON e.branch_id = b.branch_id WHERE e.employee_id=?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeResultSetExtractor(), employeeId);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public Employee findByEmail(String email) {
		String sql = "SELECT * FROM employees WHERE email=?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeMapper(), email);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public Employee findByEmailWithRelations(String email) {
		String sql = "SELECT \r\n" + "    e.*,\r\n" + "    es.name,\r\n" + "    g.gender_id,  \r\n"
				+ "    er.name,    \r\n" + "    b.name    \r\n" + "FROM employees e\r\n"
				+ "LEFT JOIN employee_statuses es \r\n" + "    ON e.employee_status_id = es.employee_status_id\r\n"
				+ "LEFT JOIN genders g \r\n" + "    ON e.gender_id = g.gender_id\r\n"
				+ "LEFT JOIN employee_roles er \r\n" + "    ON e.employee_role_id = er.role_id\r\n"
				+ "LEFT JOIN branches b \r\n" + "    ON e.branch_id = b.branch_id WHERE e.email=?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeResultSetExtractor(), email);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public Employee findByEmailAndPassword(String email, String password) {
		String sql = "SELECT * FROM employees WHERE email = ? AND password = ?";
		List<Employee> entities = jdbcTemplate.query(sql, new EmployeeMapper(), email, password);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int add(Employee employee) {

		if (employee.getEmployeeId() == null || employee.getEmployeeId().trim().isEmpty()) {
			String maxIdSql = "SELECT COALESCE(MAX(CAST(employee_id AS UNSIGNED)), 0) + 1 FROM employees";
			Integer nextId = jdbcTemplate.queryForObject(maxIdSql, Integer.class);
			employee.setEmployeeId(String.valueOf(nextId));
		}

		if (employee.getCreatedAt() == null) {
			employee.setCreatedAt(LocalDateTime.now());
		}

		String sql = "INSERT INTO employees (employee_id, name, email, photopath, password, employee_status_id, phone, salary, address, dob, gender_id, employee_role_id, branch_id, created_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, employee.getEmployeeId(), employee.getName(), employee.getEmail(),
				employee.getPhotoPath(), employee.getPassword(), employee.getEmployeeStatusId(), employee.getPhone(),
				employee.getSalary(), employee.getAddress(), employee.getDob(), employee.getGenderId(),
				employee.getEmployeeRoleId(), employee.getBranchId(), employee.getCreatedAt());
	}

	public int updateEmployee(Employee employee) {
		String sql = "UPDATE employees SET name = ?, email = ?, employee_status_id = ?, phone = ?, salary = ?, address = ?, dob = ?, gender_id = ?, employee_role_id = ?, branch_id = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, employee.getName(), employee.getEmail(), employee.getEmployeeStatusId(),
				employee.getPhone(), employee.getSalary(), employee.getAddress(), employee.getDob(),
				employee.getGenderId(), employee.getEmployeeRoleId(), employee.getBranchId(), employee.getEmployeeId());
	}

	public int changeName(String employee_id, String name) {
		String sql = "UPDATE employees SET name = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, name, employee_id);
	}

	public int changeEmail(String employee_id, String email) {
		String sql = "UPDATE employees SET email = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, email, employee_id);
	}

	public int changeProfile(String employee_id, String name, String phone, String address, String photopath) {
		if (photopath == null || photopath.isEmpty()) {
			String sql = "UPDATE employees SET name = ?, phone = ?, address = ? WHERE employee_id = ?";
			return jdbcTemplate.update(sql, name, phone, address, employee_id);
		}
		String sql = "UPDATE employees SET name = ?, phone = ?, address = ?, photopath = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, name, phone, address, photopath, employee_id);
	}

	public int changePassword(String employee_id, String password) {
		String sql = "UPDATE employees SET password = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, password, employee_id);
	}

	public int changeStatus(String employee_id, String employee_status_id) {
		String sql = "UPDATE employees SET employee_status_id = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, employee_status_id, employee_id);
	}

	public int changeRole(String employee_id, String employee_role_id) {
		String sql = "UPDATE employees SET employee_role_id = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, employee_role_id, employee_id);
	}

	public int delete(String employee_id) {
		String sql = "DELETE FROM employees WHERE employee_id = ?";
		return jdbcTemplate.update(sql, employee_id);
	}
}