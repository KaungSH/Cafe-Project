package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.PayMethod;

public class PayMethodMapper implements RowMapper<PayMethod> {

	@Override
	public PayMethod mapRow(ResultSet rs, int rowNum) throws SQLException {
		PayMethod pm = new PayMethod();
		pm.setMethodId(rs.getString("method_id"));
		pm.setName(rs.getString("name"));
		pm.setDescription(rs.getString("description"));
		pm.setLogoPath(rs.getString("logopath"));
		pm.setActive(rs.getBoolean("is_active"));
		pm.setEdited(rs.getBoolean("isedited"));
		pm.setDeleted(rs.getBoolean("isdeleted"));
		pm.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
		pm.setEmployeeId(rs.getString("employee_id"));
		return pm;
	}
}