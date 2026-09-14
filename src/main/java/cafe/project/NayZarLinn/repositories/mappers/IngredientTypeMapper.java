package cafe.project.NayZarLinn.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.NayZarLinn.repositories.entities.IngredientType;

public class IngredientTypeMapper implements RowMapper<IngredientType> {

	@Override
	public IngredientType mapRow(ResultSet rs, int rowNum) throws SQLException {

		

		IngredientType it = new IngredientType();
		
		it.setIngredient_type_id(rs.getString("ingredient_type_id"));
		it.setName(rs.getString("name"));
		it.setDescription(rs.getString("Description"));
		it.setUnit_id(rs.getString("unit_id"));
		it.setAbbreviation(rs.getString("abbreviation"));
		it.setIsdeleted(rs.getBoolean("isdeleted"));
		it.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());;
		return it;
	}

}
