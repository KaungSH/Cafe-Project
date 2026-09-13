package cafe.project.YatiWinLatt.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.YatiWinLatt.models.SizeEntryDto;
import cafe.project.YatiWinLatt.models.SizeListDto;

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

	public List<SizeListDto> findAll() {
		String sql = "SELECT s.size_id, s.size_code, s.name, s.is_active, s.created_at, e.name AS employee_name "
				+ "FROM sizes s " + "LEFT JOIN employees e ON s.employee_id = e.employee_id "
				+ "WHERE s.isdeleted = false " + "ORDER BY s.created_at DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SizeListDto.class));
	}

	public SizeEntryDto findById(String id) {
		String sql = "SELECT size_id, size_code, name, is_active, employee_id FROM sizes WHERE size_id = ? AND isdeleted = false";
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(SizeEntryDto.class), id);
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

	public int deleteById(String id) {
		String sql = "UPDATE sizes SET isdeleted = true WHERE size_id = ?";
		return jdbcTemplate.update(sql, id);
	}

	public List<Map<String, Object>> findAllEmployees() {

		String sql = "SELECT employee_id, name FROM employees";
		return jdbcTemplate.queryForList(sql);
	}
}