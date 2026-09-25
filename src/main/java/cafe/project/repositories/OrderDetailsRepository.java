package cafe.project.repositories;

import java.util.List;

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

		String sql = "SELECT * FROM order_details";

		return jdbcTemplate.query(sql, new OrderDetailsMapper());
	}
	
	public List<OrderDetails> findAllByRelation() {

		String sql = "SELECT * FROM order_details";

		return jdbcTemplate.query(sql, new OrderDetailsMapper());
	}

	// GET BY ID
	public OrderDetails findById(String id) {

		String sql = "SELECT * FROM order_details WHERE order_detail_id = ?";

		return jdbcTemplate.queryForObject(
				sql,
				new OrderDetailsMapper(),
				id);
	}

	// GET BY ORDER ID
	public List<OrderDetails> findByOrderId(String orderId) {

		String sql = "SELECT * FROM order_details WHERE order_id = ?";

		return jdbcTemplate.query(
				sql,
				new OrderDetailsMapper(),
				orderId);
	}

	// SAVE
	public int save(OrderDetails entity) {

		String sql = "INSERT INTO order_details "
				+ "(order_detail_id, product_id, order_id, quantity, remark) "
				+ "VALUES (?, ?, ?, ?, ?)";

		return jdbcTemplate.update(sql,
				entity.getOrder_detail_id(),
				entity.getProduct_id(),
				entity.getOrder_id(),
				entity.getQuantity(),
				entity.getRemark());
	}

	// UPDATE
	public int edit(String id, OrderDetails entity) {

		String sql = "UPDATE order_details SET "
				+ "product_id = ?, "
				+ "order_id = ?, "
				+ "quantity = ?, "
				+ "remark = ? "
				+ "WHERE order_detail_id = ?";

		return jdbcTemplate.update(sql,
				entity.getProduct_id(),
				entity.getOrder_id(),
				entity.getQuantity(),
				entity.getRemark(),
				id);
	}

	// DELETE
	public int delete(String id) {

		String sql = "DELETE FROM order_details WHERE order_detail_id = ?";

		return jdbcTemplate.update(sql, id);
	}
}
