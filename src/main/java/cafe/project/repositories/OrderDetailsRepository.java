package cafe.project.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repositories.entities.OrderDetails;
import cafe.project.repositories.mappers.OrderDetailsMapper;

@Repository
public class OrderDetailsRepository {

	private final JdbcTemplate jdbcTemplate;

	public OrderDetailsRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// GET ALL
	public List<OrderDetails> findAll() {

		String sql = "SELECT od.*, pt.name AS product_name\r\n" + "FROM order_details od\r\n"
				+ "LEFT JOIN products p ON od.product_id = p.product_id\r\n"
				+ "LEFT JOIN product_types pt ON p.item_id = pt.type_id";

		return jdbcTemplate.query(sql, new OrderDetailsMapper());
	}

	// GET BY ID
	public OrderDetails findById(String id) {

		String sql = "SELECT od.*, pt.name AS product_name \r\n" + "FROM order_details od \r\n"
				+ "LEFT JOIN products p ON od.product_id = p.product_id \r\n"
				+ "LEFT JOIN product_types pt ON p.item_id = pt.type_id \r\n" + "WHERE od.order_detail_id = ?";

		return jdbcTemplate.queryForObject(sql, new OrderDetailsMapper(), id);
	}

	// GET BY ORDER ID
	public List<OrderDetails> findByOrderId(String orderId) {

		String sql =
		        "SELECT od.*, "
		      + "pt.name AS product_name, "
		      + "p.price AS price "
		      + "FROM order_details od "
		      + "LEFT JOIN products p "
		      + "ON od.product_id = p.product_id "
		      + "LEFT JOIN product_types pt "
		      + "ON p.item_id = pt.type_id "
		      + "WHERE od.order_id = ?";

	    return jdbcTemplate.query(
	            sql,
	            new OrderDetailsMapper(),
	            orderId
	    );
	}

	public List<String> findOrderIds() {

		String sql = "SELECT order_id FROM orders WHERE isdeleted = false";

		return jdbcTemplate.queryForList(sql, String.class);
	}

	// SAVE
	public int save(OrderDetails entity) {

		String sql = "INSERT INTO order_details\r\n" + "(order_detail_id, product_id, order_id, quantity, remark) \r\n"
				+ "VALUES (?, ?, ?, ?, ?);\r\n";

		return jdbcTemplate.update(sql, UUID.randomUUID().toString(), entity.getProduct_id(), entity.getOrder_id(),
				entity.getQuantity(), entity.getRemark());
	}

	public BigDecimal calculateTotalAmount(String orderId) {

		String sql = "SELECT COALESCE(SUM(p.price * od.quantity), 0) " + "FROM order_details od " + "JOIN products p "
				+ "ON od.product_id = p.product_id " + "WHERE od.order_id = ?";

		return jdbcTemplate.queryForObject(sql, BigDecimal.class, orderId);
	}

	// UPDATE
	public int edit(String id, OrderDetails entity) {

		String sql = "UPDATE order_details SET \r\n" + "product_id = ?,order_id = ?, quantity = ?, remark = ? \r\n"
				+ "WHERE order_detail_id = ?";

		return jdbcTemplate.update(sql, entity.getProduct_id(), entity.getOrder_id(), entity.getQuantity(),
				entity.getRemark(), id);
	}

	// DELETE
	public int delete(String id) {

		String sql = "DELETE FROM order_details WHERE order_detail_id = ?";

		return jdbcTemplate.update(sql, id);
	}
}
