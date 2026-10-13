package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.Size;

public class SizeMapper implements RowMapper<Size> {
	@Override
	public Size mapRow(ResultSet rs, int rowNum) throws SQLException {
		Size entity = new Size();
		entity.setSize_id(rs.getString("size_id"));
		entity.setEmployee_id(rs.getString("employee_id"));
		entity.setName(rs.getString("name"));
		entity.setSize_code(rs.getString("size_code"));
		entity.setIs_active(rs.getBoolean("is_active"));
		entity.setIsedited(rs.getBoolean("isedited"));
		entity.setIsdeleted(rs.getBoolean("isdeleted"));

		Timestamp timestamp = rs.getTimestamp("created_at");
		if (timestamp != null) {
			entity.setCreated_at(timestamp.toLocalDateTime());
		}

		return entity;
	}

}
