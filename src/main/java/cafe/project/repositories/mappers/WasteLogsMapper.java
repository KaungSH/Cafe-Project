package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.WasteLogs;

public class WasteLogsMapper implements RowMapper<WasteLogs> {

	@Override
	public WasteLogs mapRow(ResultSet rs, int rowNum) throws SQLException {
		WasteLogs entity = new WasteLogs();

		entity.setWaste_id(rs.getString("waste_id"));
		entity.setBatch_id(rs.getString("batch_id"));
		entity.setWaste_reason_id(rs.getString("waste_reason_id"));
		entity.setQuantity_lost(rs.getBigDecimal("quantity_lost"));
		entity.setFinancial_loss(rs.getBigDecimal("financial_loss"));
		entity.setEmployee_id(rs.getString("employee_id"));
		entity.setNotes(rs.getString("notes"));
		entity.setIsdeleted(rs.getBoolean("isdeleted"));

		Timestamp loggedAt = rs.getTimestamp("logged_at");
		if (loggedAt != null) {
			entity.setLogged_at(loggedAt.toLocalDateTime());
		}

		return entity;
	}
}
