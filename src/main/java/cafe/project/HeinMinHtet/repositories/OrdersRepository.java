package cafe.project.HeinMinHtet.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.HeinMinHtet.repositories.entities.Orders;
import cafe.project.HeinMinHtet.repositories.mappers.OrdersMapper;

@Repository
public class OrdersRepository {

	private final JdbcTemplate jdbcTemplate;
	

	public OrdersRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public List<Orders> findAll() {

		String sql = "SELECT * FROM orders WHERE isdeleted = false";

		return jdbcTemplate.query(sql, new OrdersMapper());
	}
	
	public List<Orders> findAllByRelation() {

		String sql = "SELECT * FROM orders WHERE isdeleted = false";

		return jdbcTemplate.query(sql, new OrdersMapper());
	}
	

	public List<Orders> findDeletedAll() {

		String sql = "SELECT * FROM orders WHERE isdeleted = true";

		return jdbcTemplate.query(sql, new OrdersMapper());
	}

	
	public Orders findById(String id) {

		String sql = "SELECT * FROM orders WHERE order_id = ?";

		return jdbcTemplate.queryForObject(sql, new OrdersMapper(), id);
	}


	public int save(Orders entity) {

		String sql = "INSERT INTO orders "
				+ "(order_id, employee_id, customer_id, branch_id, "
				+ "created_time, received_time, isedited, isdeleted, "
				+ "order_type_id, total_amount) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		return jdbcTemplate.update(sql,
				entity.getOrder_id(),
				entity.getEmployee_id(),
				entity.getCustomer_id(),
				entity.getBranch_id(),
				entity.getCreated_time(),
				entity.getReceived_time(),
				entity.isIsedited(),
				entity.isIsdeleted(),
				entity.getOrder_type_id(),
				entity.getTotal_amount());
	}

	public int edit(String id, Orders entity) {

		String sql = "UPDATE orders SET "
				+ "employee_id = ?, "
				+ "customer_id = ?, "
				+ "branch_id = ?, "
				+ "received_time = ?, "
				+ "order_type_id = ?, "
				+ "total_amount = ?, "
				+ "isedited = 1 "
				+ "WHERE order_id = ?";

		return jdbcTemplate.update(sql,
				entity.getEmployee_id(),
				entity.getCustomer_id(),
				entity.getBranch_id(),
				entity.getReceived_time(),
				entity.getOrder_type_id(),
				entity.getTotal_amount(),
				id);
	}


	public int delete(String id) {

		String sql = "UPDATE orders SET isdeleted = 1 WHERE order_id = ?";

		return jdbcTemplate.update(sql, id);
	}
}
