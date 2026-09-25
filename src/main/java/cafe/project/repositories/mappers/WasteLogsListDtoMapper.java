package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

import cafe.project.models.WasteLogsListDto;

public class WasteLogsListDtoMapper implements RowMapper<WasteLogsListDto> {

	@Override
	public WasteLogsListDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		WasteLogsListDto dto = new WasteLogsListDto();

		dto.setWaste_id(rs.getString("waste_id"));
		dto.setBatch_id(rs.getString("batch_id"));
		dto.setReason_name(rs.getString("reason_name"));
		dto.setQuantity_lost(rs.getBigDecimal("quantity_lost"));
		dto.setFinancial_loss(rs.getBigDecimal("financial_loss"));
		dto.setName(rs.getString("name"));
		dto.setNotes(rs.getString("notes"));

		Timestamp loggedAt = rs.getTimestamp("logged_at");
		if (loggedAt != null) {
			dto.setLogged_at(loggedAt.toLocalDateTime());
		}

		return dto;
	}
}
