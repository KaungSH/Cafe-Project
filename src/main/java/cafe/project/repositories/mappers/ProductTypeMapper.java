package cafe.project.KaungSattHein.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.KaungSattHein.repositories.entities.ProductType;

public class ProductTypeMapper implements RowMapper<ProductType> {

	@Override
	public ProductType mapRow(ResultSet rs, int rowNum) throws SQLException {
		return  new ProductType(rs.getString("type_id"), rs.getString("name"), rs.getString("description"), rs.getString("coverimgpath"), rs.getDouble("price"), rs.getObject("created_at", LocalDateTime.class), rs.getBoolean("isdeleted"), rs.getBoolean("isedited"), rs.getString("category_id"), rs.getString("employee_id"));
	}

}
