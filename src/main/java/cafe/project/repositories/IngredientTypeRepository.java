package cafe.project.NayZarLinn.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.NayZarLinn.repositories.entities.IngredientType;
import cafe.project.NayZarLinn.repositories.mappers.IngredientTypeMapper;

@Repository
public class IngredientTypeRepository {

	private final JdbcTemplate jdbcTemplate;

	public IngredientTypeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<IngredientType> findAll() {
		String sql = "SELECT i.*, u.abbreviation FROM ingredient_types i LEFT JOIN units u ON i.unit_id = u.unit_id WHERE i.isdeleted = 0";
		return jdbcTemplate.query(sql, new IngredientTypeMapper());
	}

	public List<IngredientType> findAllDeleted() {
		String sql = "SELECT i.*, u.abbreviation FROM ingredient_types i LEFT JOIN units u ON i.unit_id = u.unit_id WHERE i.isdeleted = 1";
		return jdbcTemplate.query(sql, new IngredientTypeMapper());
	}

	public Optional<IngredientType> findById(String id) {
		String sql = "SELECT i.*, u.abbreviation FROM ingredient_types i LEFT JOIN units u ON i.unit_id = u.unit_id WHERE i.ingredient_type_id = ?";
		List<IngredientType> results = jdbcTemplate.query(sql, new IngredientTypeMapper(), id);
		return results.stream().findFirst();
	}

	public int save(IngredientType item) {
		String sql = "INSERT INTO ingredient_types (ingredient_type_id, name, description, unit_id, isdeleted, created_at) VALUES (?, ?, ?, ?, 0, NOW())";
		return jdbcTemplate.update(sql, item.getIngredientTypeId(), item.getName(), item.getDescription(),
				item.getUnitId());
	}

	public int update(IngredientType item) {
		String sql = "UPDATE ingredient_types SET name = ?, description = ?, unit_id = ? WHERE ingredient_type_id = ?";
		return jdbcTemplate.update(sql, item.getName(), item.getDescription(), item.getUnitId(),
				item.getIngredientTypeId());
	}

	public int softDelete(String id) {
		String sql = "UPDATE ingredient_types SET isdeleted = 1 WHERE ingredient_type_id = ?";
		return jdbcTemplate.update(sql, id);
	}

	public int restore(String id) {
		String sql = "UPDATE ingredient_types SET isdeleted = 0 WHERE ingredient_type_id = ?";
		return jdbcTemplate.update(sql, id);
	}

	public int hardDelete(String id) {
		String sql = "DELETE FROM ingredient_types WHERE ingredient_type_id = ?";
		return jdbcTemplate.update(sql, id);
	}
}