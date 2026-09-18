package cafe.project.HeinMinHtet.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.HeinMinHtet.repositories.entities.Unit;
import cafe.project.HeinMinHtet.repositories.mappers.UnitMapper;
import cafe.project.HeinMinHtet.repositories.mappers.UnitMapper2;
@Repository
public class UnitRepository {

	private final JdbcTemplate jdbcTemplate;

	public UnitRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// GET ALL
	public List<Unit> findAll() {

		String sql = "SELECT * FROM units WHERE isdeleted = false";

		return jdbcTemplate.query(sql, new UnitMapper());
	}
	public List<Unit> findAllByRelation() {

		String sql = "SELECT u.*,e.name employee_name FROM units u LEFT JOIN employees e ON u.employee_id=e.employee_id WHERE isdeleted = false ";

		return jdbcTemplate.query(sql, new UnitMapper2());
	}

	public List<Unit> findDeletedAll() {

		String sql = "SELECT * FROM units WHERE isdeleted = true";

		return jdbcTemplate.query(sql, new UnitMapper());
	}

	// GET BY ID
	public Unit findById(String id) {

		String sql = "SELECT * FROM units WHERE unit_id = ?";

		return jdbcTemplate.queryForObject(sql, new UnitMapper(), id);
	}

	// SAVE
	public int save(Unit entity) {

		String sql = "INSERT INTO units (unit_id, employee_id, name, abbreviation,is_active, isedited, isdeleted)VALUES (?, ?, ?, ?, ?, ?, ?)";

		return jdbcTemplate.update(sql, entity.getUnit_id(), entity.getEmployee_id(), entity.getName(),
				entity.getAbbreviation(), entity.getIs_active(), entity.getIsedited(), entity.getIsdeleted());
	}

	// UPDATE
	public int edit(String id, Unit entity) {

		String sql = "UPDATE units SET name = ?, abbreviation = ?, is_active = ?, isedited = 1 WHERE unit_id = ?";

		return jdbcTemplate.update(sql, entity.getName(), entity.getAbbreviation(), entity.getIs_active(), id);
	}

	// SOFT DELETE
	public int delete(String id) {

		String sql = "UPDATE units SET isdeleted = 1 WHERE unit_id = ?";

		return jdbcTemplate.update(sql, id);
	}
}
