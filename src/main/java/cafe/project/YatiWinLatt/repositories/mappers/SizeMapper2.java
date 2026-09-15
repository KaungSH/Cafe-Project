package cafe.project.YatiWinLatt.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import cafe.project.YatiWinLatt.repositories.entities.Size;

public class SizeMapper2 implements RowMapper<Size> {

	@Override
	public Size mapRow(ResultSet rs, int rowNum) throws SQLException {
		Size size = new SizeMapper().mapRow(rs, rowNum);
		size.setEmployee_name(rs.getString("employee_name"));
		return size;
	}
}
