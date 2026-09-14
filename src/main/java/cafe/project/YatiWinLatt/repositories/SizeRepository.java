package cafe.project.YatiWinLatt.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.YatiWinLatt.models.SizeEntryDto;

import cafe.project.YatiWinLatt.repositories.entities.Size;

import cafe.project.YatiWinLatt.repositories.mappers.SizeMapper2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class SizeRepository {

	private final JdbcTemplate jdbcTemplate;

	SizeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Size> findAll() {
		String sql = "SELECT s.*, e.name AS employee_name \r\n"
				+ "FROM sizes s \r\n"
				+ "LEFT JOIN employees e ON s.employee_id = e.employee_id \r\n"
				+ "WHERE s.isdeleted = false \r\n"
				+ "ORDER BY s.created_at DESC";
		return jdbcTemplate.query(sql, new SizeMapper2());
	}

	public Size findById(String size_id) {
		String sql = "SELECT s.*, e.name AS employee_name \"\r\n"
				+ "               + \"FROM sizes s \"\r\n"
				+ "               + \"LEFT JOIN employees e ON s.employee_id = e.employee_id \"\r\n"
				+ "               + \"WHERE s.size_id = ? AND s.isdeleted = false";
		return jdbcTemplate.queryForObject(sql, new SizeMapper2(), size_id);
	}

	public int save(SizeEntryDto dto) {
		String generatedId = UUID.randomUUID().toString();
		String sql = "INSERT INTO sizes (size_id, employee_id, name, size_code, is_active, isedited, isdeleted, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, generatedId, dto.getEmployee_id(), dto.getName(), dto.getSize_code(),
				dto.getIs_active() != null ? dto.getIs_active() : true, false, false, LocalDateTime.now());
	}

	public int update(SizeEntryDto dto) {
		String sql = "UPDATE sizes SET name = ?, size_code = ?, is_active = ?, isedited = true WHERE size_id = ?";
		return jdbcTemplate.update(sql, dto.getName(), dto.getSize_code(), dto.getIs_active(), dto.getSize_id());
	}

	public int deleteById(String size_id) {
		String sql = "UPDATE sizes SET isdeleted = true WHERE size_id = ?";
		return jdbcTemplate.update(sql, size_id);
	}

	public List<Map<String, Object>> findAllEmployees() {

		String sql = "SELECT employee_id, name FROM employees";
		return jdbcTemplate.queryForList(sql);
	}
}