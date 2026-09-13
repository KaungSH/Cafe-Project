package cafe.project.NayZarLinn.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.NayZarLinn.repositories.entities.Supplier;

public class SupplierMapper implements RowMapper<Supplier> {

	@Override
	public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {

		LocalDateTime created_at = rs.getObject("created_at", LocalDateTime.class);

		return new Supplier(rs.getString("supplier_id"), rs.getString("name"), rs.getString("contact_info"),
				rs.getBoolean("isdeleted"), created_at);
	}

}
