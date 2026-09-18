package cafe.project.YinminThiriSoe.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import cafe.project.YinminThiriSoe.models.PayMethodDto;
import cafe.project.YinminThiriSoe.repositories.entities.PayMethod;
import cafe.project.YinminThiriSoe.repositories.mappers.PayMethodMapper;
import cafe.project.YinminThiriSoe.repositories.mappers.resultsetextractors.PayMethodResultSetExtractor;

@Repository
public class PayMethodRepository {

	private final JdbcTemplate jdbcTemplate;

	public PayMethodRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<PayMethod> findAll() {
		String sql = "SELECT * FROM pay_methods WHERE isdeleted = 0";
		return jdbcTemplate.query(sql, new PayMethodMapper());
	}

	public List<PayMethod> findAllActive() {
		String sql = "SELECT * FROM pay_methods WHERE isdeleted = 0 AND is_active = 1";
		return jdbcTemplate.query(sql, new PayMethodMapper());
	}

	public List<PayMethodDto> findAllWithRelations() {
		String sql = "SELECT " + "    pm.method_id   AS method_id, " + "    pm.name        AS method_name, "
				+ "    pm.description AS description, " + "    pm.logopath    AS logopath, "
				+ "    pm.is_active   AS is_active, " + "    pm.isedited    AS isedited, "
				+ "    pm.isdeleted   AS isdeleted, " + "    pm.created_at  AS created_at, "
				+ "    pm.employee_id AS employee_id, " + "    e.name         AS employee_name "
				+ "FROM pay_methods pm " + "LEFT JOIN employees e ON pm.employee_id = e.employee_id "
				+ "WHERE pm.isdeleted = 0 " + "ORDER BY pm.created_at DESC";

		return jdbcTemplate.query(sql, new PayMethodResultSetExtractor());
	}

	public PayMethod findById(String methodId) {
		String sql = "SELECT * FROM pay_methods WHERE method_id = ? AND isdeleted = 0";
		List<PayMethod> list = jdbcTemplate.query(sql, new PayMethodMapper(), methodId);
		return list.isEmpty() ? null : list.get(0);
	}

	public PayMethodDto findByIdWithRelations(String methodId) {
		String sql = """
				SELECT
				    pm.method_id       AS method_id,
				    pm.name            AS method_name,
				    pm.description     AS description,
				    pm.logopath        AS logopath,
				    pm.is_active       AS is_active,
				    pm.isedited        AS isedited,
				    pm.isdeleted       AS isdeleted,
				    pm.created_at      AS created_at,
				    pm.employee_id     AS employee_id,
				    e.name             AS employee_name
				FROM pay_methods pm
				LEFT JOIN employees e ON pm.employee_id = e.employee_id
				WHERE pm.method_id = ? AND pm.isdeleted = 0
				""";

		List<PayMethodDto> results = jdbcTemplate.query(sql, new PayMethodResultSetExtractor(), methodId);

		if (results != null && !results.isEmpty()) {
			return results.get(0);
		}

		return null;
	}

	public int add(PayMethod pm) {
		String sql = "INSERT INTO pay_methods (method_id, name,description,logopath,is_active, isedited,isdeleted,created_at,employee_id) VALUES (?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, pm.getMethodId(), pm.getName(), pm.getDescription(), pm.getLogoPath(),
				pm.isActive() ? 1 : 0, pm.isEdited() ? 1 : 0, pm.isDeleted() ? 1 : 0, pm.getCreatedAt(),
				pm.getEmployeeId());
	}

	public int update(PayMethod pm) {
		String sql = "UPDATE pay_methods SET name = ?,description = ?,  logopath = ?,is_active = ?, employee_id =?,isedited= 1 WHERE method_id =?";
		return jdbcTemplate.update(sql, pm.getName(), pm.getDescription(), pm.getLogoPath(), pm.isActive() ? 1 : 0,
				pm.getEmployeeId(), pm.getMethodId());
	}

	public int softDelete(String methodId) {
		String sql = "UPDATE pay_methods SET isdeleted = 1, isedited = 1 WHERE method_id = ?";
		return jdbcTemplate.update(sql, methodId);
	}
}
