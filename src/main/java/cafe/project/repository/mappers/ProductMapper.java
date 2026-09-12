package cafe.project.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repository.entities.Product;

public class ProductMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Product(rs.getString("product_id"), rs.getString("employee_id"),rs.getString("type_id"),
		   		rs.getString("size_id"), rs.getDouble("price"),
		   		rs.getBoolean("isedited"),rs.getBoolean("isdeleted"), rs.getBoolean("is_active"), rs.getObject("created_at", LocalDateTime.class));
	}

}
