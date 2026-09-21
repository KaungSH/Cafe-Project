package cafe.project.HeinMinHtet.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import cafe.project.HeinMinHtet.models.OrderStatus;
import cafe.project.HeinMinHtet.repositories.entities.Orders;

@Component
public class OrdersMapper implements RowMapper<Orders> {

	@Override
	public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
		Orders order = new Orders();

		order.setOrder_id(rs.getString("order_id"));
		order.setEmployee_id(rs.getString("employee_id"));
		order.setCustomer_id(rs.getString("customer_id"));
		order.setBranch_id(rs.getString("branch_id"));
		order.setRegister_id(rs.getString("register_id")); 
		order.setCreated_time(
				rs.getTimestamp("created_time") != null ? rs.getTimestamp("created_time").toLocalDateTime() : null);
		order.setReceived_time(
				rs.getTimestamp("received_time") != null ? rs.getTimestamp("received_time").toLocalDateTime() : null);
		order.setIsedited(rs.getBoolean("isedited"));
		order.setIsdeleted(rs.getBoolean("isdeleted"));
		order.setOrder_type_id(rs.getString("order_type_id"));
		order.setTotal_amount(rs.getBigDecimal("total_amount"));
		order.setTokenNumber(rs.getInt("token_number"));
		order.setCustomerAddress_id(rs.getString("customer_address_id"));

		if (order.isIsdeleted()) {
			order.setOrderStatus(OrderStatus.CANCELLED);
		} else if (order.getReceived_time() == null) {
			order.setOrderStatus(OrderStatus.PENDING);
		} else {
			order.setOrderStatus(OrderStatus.COMPLETED);
		}

		return order;
	}

	public static Orders toDTO(Orders entity) {
		if (entity == null)
			return null;

		Orders dto = new Orders();
		dto.setOrder_id(entity.getOrder_id());
		dto.setEmployee_id(entity.getEmployee_id());
		dto.setCustomer_id(entity.getCustomer_id());
		dto.setBranch_id(entity.getBranch_id());
		dto.setRegister_id(entity.getRegister_id()); 
		dto.setCreated_time(entity.getCreated_time());
		dto.setReceived_time(entity.getReceived_time());
		dto.setIsedited(entity.isIsedited());
		dto.setIsdeleted(entity.isIsdeleted());
		dto.setOrder_type_id(entity.getOrder_type_id());
		dto.setTotal_amount(entity.getTotal_amount());
		dto.setTokenNumber(entity.getTokenNumber());
		dto.setCustomerAddress_id(entity.getCustomerAddress_id());

		if (entity.isIsdeleted()) {
			dto.setOrderStatus(OrderStatus.CANCELLED);
		} else if (entity.getReceived_time() == null) {
			dto.setOrderStatus(OrderStatus.PENDING);
		} else {
			dto.setOrderStatus(OrderStatus.COMPLETED);
		}

		return dto;
	}

	public static List<Orders> toDTOList(List<Orders> entityList) {
		List<Orders> dtoList = new ArrayList<>();
		if (entityList != null) {
			for (Orders entity : entityList) {
				dtoList.add(toDTO(entity));
			}
		}
		return dtoList;
	}
}