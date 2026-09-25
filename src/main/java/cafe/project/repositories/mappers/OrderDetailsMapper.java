package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.OrderDetails;

public class OrderDetailsMapper implements RowMapper<OrderDetails> {

	@Override
	public OrderDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

		OrderDetails detail = new OrderDetails();

		detail.setOrder_detail_id(rs.getString("order_detail_id"));
		detail.setProduct_id(rs.getString("product_id"));
		detail.setOrder_id(rs.getString("order_id"));
		detail.setQuantity(rs.getInt("quantity"));
		detail.setRemark(rs.getString("remark"));

		return detail;
	}
}
