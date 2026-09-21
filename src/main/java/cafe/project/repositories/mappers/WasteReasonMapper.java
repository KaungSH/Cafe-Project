package cafe.project.YatiWinLatt.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import cafe.project.YatiWinLatt.repositories.entities.WasteReason;

public class WasteReasonMapper implements RowMapper<WasteReason> {
	@Override
	public WasteReason mapRow(ResultSet rs, int rowNum) throws SQLException {
		WasteReason reason = new WasteReason();

		reason.setWaste_reason_id(rs.getString("waste_reason_id"));
		reason.setReason_name(rs.getString("reason_name"));

		return reason;
	}
}
