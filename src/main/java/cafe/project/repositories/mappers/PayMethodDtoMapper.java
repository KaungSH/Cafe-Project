package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.models.PayMethodDto;

public class PayMethodDtoMapper implements RowMapper<PayMethodDto> {

	@Override
	public PayMethodDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PayMethodDto dto = new PayMethodDto();
		dto.setMethodId(rs.getString("method_id"));
		dto.setName(rs.getString("name"));
		dto.setDescription(rs.getString("description"));
		dto.setLogoPath(rs.getString("logopath"));
		dto.setActive(rs.getBoolean("is_active"));
		dto.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
		dto.setEmployeeName(rs.getString("employee_name"));
		return dto;
	}
}