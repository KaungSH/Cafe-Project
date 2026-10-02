package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import cafe.project.repositories.entities.IngredientBatch;

public class IngredientBatchMapper implements RowMapper<IngredientBatch> {
	@Override
	public IngredientBatch mapRow(ResultSet rs, int rowNum) throws SQLException {
		IngredientBatch entity = new IngredientBatch();
		entity.setBatch_id(rs.getString("batch_id"));
		entity.setRemaining_quantity(rs.getBigDecimal("remaining_quantity"));

		if (rs.getDate("manufactured_date") != null) {
			entity.setManufactured_date(rs.getDate("manufactured_date").toLocalDate());
		}
		if (rs.getDate("expire_date") != null) {
			entity.setExpire_date(rs.getDate("expire_date").toLocalDate());
		}

		entity.setBranch_id(rs.getString("branch_id"));
		entity.setIsdeleted(rs.getBoolean("isdeleted"));

		if (rs.getTimestamp("created_at") != null) {
			entity.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
		}

		entity.setIngredient_type_id(rs.getString("ingredient_type_id"));
		entity.setUnit_cost(rs.getBigDecimal("unit_cost"));
		entity.setQuantity_ordered(rs.getDouble("quantity_ordered"));
		entity.setTotal_import_cost(rs.getDouble("total_import_cost"));
		entity.setImport_id(rs.getString("import_id"));

		try {
			entity.setBranch_name(rs.getString("branch_name"));
		} catch (SQLException ignored) {
		}

		try {
			entity.setIngredientType_name(rs.getString("ingredient_type_name"));
		} catch (SQLException ignored) {
		}

		return entity;
	}
}