package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.models.StatusDto;

public class StatusMapper implements RowMapper<StatusDto>{

	@Override
	public StatusDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new StatusDto(rs.getString("id"), rs.getString("name"));
	}

}