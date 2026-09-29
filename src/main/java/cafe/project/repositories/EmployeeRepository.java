package cafe.project.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import cafe.project.models.EmployeeEntryDto;
import cafe.project.models.EmployeeListDto;
import cafe.project.repositories.entities.Employee;
import cafe.project.repositories.mappers.EmployeeMapper;

@Repository
public class EmployeeRepository {

	private final JdbcTemplate jdbcTemplate;

	public EmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final String BASE_SELECT_LIST = "SELECT e.employee_id, e.name, e.email, e.phone, e.salary, e.address, e.dob, e.photopath, "
			+ "g AS gender, es.name AS status_name, er.name AS role_name, b.name AS branch_name "
			+ "FROM employees e " + "LEFT JOIN employee_statuses es ON e.employee_status_id = es.employee_status_id "
			+ "LEFT JOIN gender g ON e.gender = g.gender "
			+ "LEFT JOIN employee_roles er ON e.employee_role_id = er.role_id "
			+ "LEFT JOIN branches b ON e.branch_id = b.branch_id ";

	public List<Employee> findAll() {
		String sql = "SELECT * FROM employees WHERE is_deleted = 0";
		return this.jdbcTemplate.query(sql, EmployeeMapper.entityMapper);
	}

	public List<EmployeeListDto> findAllEmployeeListDto() {
		String sql = BASE_SELECT_LIST + "WHERE e.is_deleted = 0";
		return this.jdbcTemplate.query(sql, EmployeeMapper.listDtoMapper);
	}

	public List<EmployeeListDto> findDeletedEmployeeListDto() {
		String sql = BASE_SELECT_LIST + "WHERE e.is_deleted = 1";
		return this.jdbcTemplate.query(sql, EmployeeMapper.listDtoMapper);
	}

	public List<EmployeeListDto> findByStatus(String statusId) {
		String sql = BASE_SELECT_LIST + "WHERE e.is_deleted = 0 AND e.employee_status_id = ?";
	  	return this.jdbcTemplate.query(sql, EmployeeMapper.listDtoMapper, statusId);
	}

	public List<EmployeeListDto> findByRole(String roleId) {
		String sql = BASE_SELECT_LIST + "WHERE e.is_deleted = 0 AND e.employee_role_id = ?";
		return this.jdbcTemplate.query(sql, EmployeeMapper.listDtoMapper, roleId);
	}

	public List<EmployeeListDto> findByBranch(String branchId) {
		String sql = BASE_SELECT_LIST + "WHERE e.is_deleted = 0 AND e.branch_id = ?";
		return this.jdbcTemplate.query(sql, EmployeeMapper.listDtoMapper, branchId);
	}

	public Employee findById(String employeeId) {
		String sql = "SELECT * FROM employees WHERE employee_id = ?";
		List<Employee> entities = jdbcTemplate.query(sql, EmployeeMapper.entityMapper, employeeId);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public Employee findByEmail(String email) {
		String sql = "SELECT * FROM employees WHERE email = ? AND is_deleted = 0";
		List<Employee> entities = jdbcTemplate.query(sql, EmployeeMapper.entityMapper, email);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int add(EmployeeEntryDto dto) {
		if (dto.getEmployee_id() == null || dto.getEmployee_id().trim().isEmpty()) {
			String maxIdSql = "SELECT COALESCE(MAX(CAST(employee_id AS UNSIGNED)), 0) + 1 FROM employees";
			Integer nextId = jdbcTemplate.queryForObject(maxIdSql, Integer.class);
			dto.setEmployee_id(String.valueOf(nextId));
		}

		String sql = "INSERT INTO employees (employee_id, name, email, password, photopath, phone, salary, address, dob, gender_id, employee_status_id, employee_role_id, branch_id, created_at, is_deleted) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?, 0)";

		return jdbcTemplate.update(sql, dto.getEmployee_id(), dto.getName(), dto.getEmail(), dto.getPassword(),
				dto.getPhotopath(), dto.getPhone(), dto.getSalary(), dto.getAddress(), dto.getDob(), dto.getGender(),
				dto.getEmployee_status_id(), dto.getEmployee_role_id(), dto.getBranch_id(), LocalDateTime.now());
	}

	public int updateEmployee(EmployeeEntryDto dto) {
		String sql = "UPDATE employees SET name = ?, email = ?, phone = ?, salary = ?, address = ?, dob = ?, gender_id = ?, employee_status_id = ?, employee_role_id = ?, branch_id = ? WHERE employee_id = ?";
		return jdbcTemplate.update(sql, dto.getName(), dto.getEmail(), dto.getPhone(), dto.getSalary(),
				dto.getAddress(), dto.getDob(), dto.getGender(), dto.getEmployee_status_id(), dto.getEmployee_role_id(),
				dto.getBranch_id(), dto.getEmployee_id());
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

	public int softDelete(String employee_id) {
		String sql = "UPDATE employees SET is_deleted = 1 WHERE employee_id = ?";
		return jdbcTemplate.update(sql, employee_id);
	}

	public int recover(String employee_id) {
		String sql = "UPDATE employees SET is_deleted = 0 WHERE employee_id = ?";
		return jdbcTemplate.update(sql, employee_id);
	}

	public int hardDelete(String employee_id) {
		String sql = "DELETE FROM employees WHERE employee_id = ?";
		return jdbcTemplate.update(sql, employee_id);
	}
}