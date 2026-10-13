package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.models.RoleDto;

public class RoleMapper implements RowMapper<RoleDto>{

	@Override
	public RoleDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new RoleDto(rs.getString("role_id"), rs.getString("name"), rs.getString("description"), rs.getBoolean("isedited"), rs.getBoolean("isdeleted"), rs.getObject("created_at", LocalDateTime.class));
	}

}