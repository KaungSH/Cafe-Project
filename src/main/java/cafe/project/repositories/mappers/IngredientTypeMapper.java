package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.IngredientType;

public class IngredientTypeMapper implements RowMapper<IngredientType> {

	@Override
	public IngredientType mapRow(ResultSet rs, int rowNum) throws SQLException {

		IngredientType item = new IngredientType();
		item.setIngredientTypeId(rs.getString("ingredient_type_id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setUnitId(rs.getString("unit_id"));
		item.setIsDeleted(rs.getBoolean("isdeleted"));
		item.setUnitAbbreviation(rs.getString("abbreviation"));
		item.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));

		return item;
	}

}
