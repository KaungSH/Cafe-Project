package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.Orders;

public class OrdersMapper implements RowMapper<Orders> {

	@Override
	public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {

		Orders order = new Orders();

		order.setOrder_id(rs.getString("order_id"));
		order.setEmployee_id(rs.getString("employee_id"));
		order.setBranch_id(rs.getString("branch_id"));
		
		order.setCreated_time(rs.getTimestamp("created_time").toLocalDateTime());
		order.setReceived_time(rs.getTimestamp("received_time") != null ? rs.getTimestamp("received_time").toLocalDateTime() : null);
		
		order.setIsedited(rs.getBoolean("isedited"));
		order.setIsdeleted(rs.getBoolean("isdeleted"));
		
		order.setTotal_amount(rs.getBigDecimal("total_amount"));
		order.setToken_number(rs.getInt("token_number"));
		
		order.setEmployee_name(rs.getString("employee_name"));
		order.setBranch_name(rs.getString("branch_name"));

		return order;
	}
}
