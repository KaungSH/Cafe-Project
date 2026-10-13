package cafe.project.employeemanagement.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.employeemanagement.models.LoginDto;

public class LoginMapper implements RowMapper<LoginDto>{

	@Override
	public LoginDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new LoginDto(rs.getString("email"), rs.getString("password"));
	}

}
