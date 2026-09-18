package cafe.project.NayZarLinn.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.NayZarLinn.repositories.entities.IngredientType;

public class IngredientTypeMapper implements RowMapper<IngredientType> {

	@Override
	public IngredientType mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		LocalDateTime created_at = rs.getObject("created_at", LocalDateTime.class);
		
		return new IngredientType(
				rs.getString("ingredient_type_id"),
				rs.getString("name"),
				rs.getString("description"),
				rs.getString("unit_id"),
				rs.getBoolean("isdeleted"),
				created_at
				);
	}

}
