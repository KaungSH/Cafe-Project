package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.AtypeAndPtype;


public class AudienceTypeMapper implements RowMapper<AtypeAndPtype>  {

	@Override
	public AtypeAndPtype mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new AtypeAndPtype(rs.getString("audience_type_id"), rs.getString("type_name"), false);
	}

}
