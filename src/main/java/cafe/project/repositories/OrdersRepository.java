package cafe.project.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repositories.entities.Orders;
import cafe.project.repositories.mappers.OrdersMapper;

@Repository
public class OrdersRepository {

	private final JdbcTemplate jdbcTemplate;

	public OrdersRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Orders> findAll() {

		String sql = "SELECT o.*,\r\n" + "e.name AS employee_name, \r\n" + "b.name AS branch_name \r\n"
				+ "FROM orders o \r\n" + "LEFT JOIN employees e ON o.employee_id = e.employee_id \r\n"
				+ "LEFT JOIN branches b ON o.branch_id = b.branch_id \r\n" + "WHERE o.isdeleted = false";

		return jdbcTemplate.query(sql, new OrdersMapper());
	}

	public List<Orders> findAllByRelation() {

		String sql = "SELECT * FROM orders WHERE isdeleted = false";

		return jdbcTemplate.query(sql, new OrdersMapper());
	}

	public List<Orders> findDeletedAll() {

		String sql = "SELECT o.*, \r\n" + "e.name AS employee_name, \r\n" + "b.name AS branch_name \r\n"
				+ "FROM orders o \r\n" + "LEFT JOIN employees e \r\n" + "ON o.employee_id = e.employee_id \r\n"
				+ "LEFT JOIN branches b \r\n" + "ON o.branch_id = b.branch_id \r\n" + "WHERE o.isdeleted = true";

		return jdbcTemplate.query(sql, new OrdersMapper());
	}

	public Orders findById(String id) {

		String sql = "SELECT o.*, e.name AS employee_name, b.name AS branch_name FROM orders o \r\n"
				+ "LEFT JOIN employees e ON o.employee_id = e.employee_id \r\n"
				+ "LEFT JOIN branches b ON o.branch_id = b.branch_id \r\n" + "WHERE o.order_id = ?";

		return jdbcTemplate.queryForObject(sql, new OrdersMapper(), id);
	}

	public int save(Orders entity) {

		String orderId = UUID.randomUUID().toString();

		entity.setOrder_id(orderId);

		entity.setCreated_time(java.time.LocalDateTime.now());

		String sql = "INSERT INTO orders " + "(order_id, employee_id,branch_id, "
				+ "created_time, received_time, isedited, isdeleted, total_amount, token_number) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		return jdbcTemplate.update(sql, entity.getOrder_id(), entity.getEmployee_id(), entity.getBranch_id(),
				entity.getCreated_time(), entity.getReceived_time(), entity.isIsedited(), entity.isIsdeleted(),
				entity.getTotal_amount(), entity.getToken_number());
	}

	public int edit(String id, Orders entity) {

		String sql = "UPDATE orders SET " + "employee_id = ?, " + "branch_id = ?, " + "received_time = ?, "
				+ "total_amount = ?, " + "isedited = 1 " + "WHERE order_id = ?";

		return jdbcTemplate.update(sql, entity.getEmployee_id(), entity.getBranch_id(), entity.getReceived_time(),
				entity.getTotal_amount(), id);
	}

	public int delete(String id) {

		String sql = "UPDATE orders SET isdeleted = 1 WHERE order_id = ?";

		return jdbcTemplate.update(sql, id);
	}

	public int restore(String id) {

		String sql = "UPDATE orders " + "SET isdeleted = 0 " + "WHERE order_id = ?";

		return jdbcTemplate.update(sql, id);
	}

	public int permanentDelete(String id) {

		String deleteDetails = "DELETE FROM order_details WHERE order_id = ?";

		jdbcTemplate.update(deleteDetails, id);

		String deleteOrder = "DELETE FROM orders WHERE order_id = ?";

		return jdbcTemplate.update(deleteOrder, id);
	}
}
