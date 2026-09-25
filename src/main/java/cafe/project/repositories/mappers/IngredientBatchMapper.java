package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.IngredientBatch;

public class IngredientBatchMapper implements RowMapper<IngredientBatch> {

	@Override
	public IngredientBatch mapRow(ResultSet rs, int rowNum) throws SQLException {

		LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);

		LocalDate manufacturedDate = rs.getObject("manufactured_date", LocalDate.class);

		LocalDate expireDate = rs.getObject("expire_date", LocalDate.class);

		return new IngredientBatch(rs.getString("batch_id"), rs.getBigDecimal("remaining_quantity"), manufacturedDate,
				expireDate, rs.getString("branch_id"), rs.getString("import_detail_id"), rs.getBoolean("isExpired"),
				rs.getBoolean("isDeleted"), createdAt, rs.getString("ingredient_type_id"),
				rs.getBigDecimal("unit_cost"), rs.getString("branch_name"), rs.getString("ingredient_type_name"));
	}

}
