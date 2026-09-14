package cafe.project.NayZarLinn.repositories;

import java.util.List;

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
		String sql = "SELECT * FROM ingredient_types";
		List<IngredientType> entities = this.jdbcTemplate.query(sql, new IngredientTypeMapper());
		return entities;
	}

	public IngredientType findById(String ingredient_type_id) {
		String sql = "SELECT * FROM ingredient_types WHERE ingredient_type_id=? AND isdeleted=0";
		List<IngredientType> entities = this.jdbcTemplate.query(sql, new IngredientTypeMapper(), ingredient_type_id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	
	public int save(IngredientType entity) {
		String sql = "INSERT INTO ingredient_types (ingredient_type_id,name,"
				+ "description,unit_id,isdeleted,created_at) VALUES(?,?,?,?,?,?)";
		return this.jdbcTemplate.update(sql, entity.getIngredient_type_id(), entity.getName(), entity.getDescription(),
				entity.getUnit_id(), entity.isIsdeleted(), entity.getCreated_at());
	}

	public int edit(IngredientType entity) {
		String sql = "UPDATE ingredient_types SET name=?,description=?, unit_id=?, isdeleted=?, created_at=? WHERE ingredient_type_id=?";
		return jdbcTemplate.update(sql, entity.getName(), entity.getDescription(), entity.getUnit_id(),
				entity.isIsdeleted(), entity.getCreated_at(),entity.getIngredient_type_id());
	}

	public int delete(String ingredient_type_id) {
		String sql = "UPDATE ingredient_types SET isdeleted=true WHERE ingredient_type_id=?";
		return jdbcTemplate.update(sql, ingredient_type_id);
	}
	
	public List<IngredientType> findDeleted() {
		String sql = "SELECT * FROM ingredient_types WHERE isdeleted=0";
		return this.jdbcTemplate.query(sql, new IngredientTypeMapper());
	}

	public int restore(String ingredient_type_id) {
		String sql = "UPDATE ingredient_types SET isdeleted = false WHERE ingredient_type_id=?";
		return jdbcTemplate.update(sql, ingredient_type_id);
	}
	
	public int realDelete(String ingredient_type_id) {
		String sql = "DELETE FROM ingredient_types WHERE ingredient_type_id=?";
		return this.jdbcTemplate.update(sql, ingredient_type_id);
	}
}
