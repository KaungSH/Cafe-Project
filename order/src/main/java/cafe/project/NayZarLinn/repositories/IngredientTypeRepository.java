package cafe.project.NayZarLinn.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.NayZarLinn.repositories.entities.IngredientType;
import cafe.project.NayZarLinn.repositories.entities.Supplier;
import cafe.project.NayZarLinn.repositories.mappers.IngredientTypeMapper;
import cafe.project.NayZarLinn.repositories.mappers.SupplierMapper;

@Repository
public class IngredientTypeRepository {

	private final JdbcTemplate jdbcTemplate;

	public IngredientTypeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<IngredientType> findAll() {
		String sql = "SELECT \r\n" + "    it.ingredient_type_id,\r\n" + "    it.name AS ingredient_type_name,\r\n"
				+ "    it.description,\r\n" + "    u.name AS unit_name\r\n" + "FROM ingredient_types it\r\n"
				+ "JOIN units u ON it.unit_id = u.unit_id";
		List<IngredientType> entities = this.jdbcTemplate.query(sql, new IngredientTypeMapper());
		return entities;
	}

	public IngredientType findById(String ingredient_type_id) {
		String sql = "SELECT \r\n"
				+ "    it.ingredient_type_id,\r\n"
				+ "    it.name AS ingredient_type_name,\r\n"
				+ "    it.description,\r\n"
				+ "    u.name AS unit_name\r\n"
				+ "FROM ingredient_types it\r\n"
				+ "JOIN units u ON it.unit_id = u.unit_id\r\n"
				+ "WHERE ingredient_type_id=?";
		List<IngredientType> entities = this.jdbcTemplate.query(sql, new IngredientTypeMapper(), ingredient_type_id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int save(IngredientType entity) {
		String sql = "INSERT INTO ingredient_types (ingredient_type_id,name,"
				+ "description,unit_id,isdeleted,created_at) VALUES(?,?,?,?,?,?)";
		return this.jdbcTemplate.update(sql, entity.getIngredient_type_id(), entity.getName(), entity.getDescription(),
				entity.getUnit_id(), entity.isIsdeleted(), entity.getCreated_at());
	}

	public int edit(String ingredient_type_id, IngredientType entity) {
		String sql = "UPDATE ingredient_types SET name=?,description=?, unit_id=?, isdeleted=?, created_at=? WHERE ingredient_type_id=?";
		return jdbcTemplate.update(sql, entity.getName(), entity.getDescription(), entity.getUnit_id(),
				entity.isIsdeleted(), entity.getCreated_at());
	}

	public int delete(String ingredient_type_id) {
		String sql = "DELETE FROM ingredient_types WHERE ingredient_type_id";
		return jdbcTemplate.update(sql, ingredient_type_id);
	}
}
