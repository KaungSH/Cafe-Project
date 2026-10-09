package cafe.project.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repositories.entities.Payment;
import cafe.project.repositories.mappers.PaymentMapper;

@Repository
public class PaymentRepository {

	private final JdbcTemplate jdbcTemplate;

	public PaymentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Payment> findAll() {

		String sql = "SELECT " + "p.*, " + "o.token_number, " + "pm.name AS pay_method_name, "
				+ "e.name AS employee_name, " + "b.name AS branch_name, " + "o.total_amount, "
				+ "GROUP_CONCAT(pt.name SEPARATOR ', ') AS product_name " + "FROM payments p " + "LEFT JOIN orders o "
				+ "ON p.order_id = o.order_id " + "LEFT JOIN pay_methods pm " + "ON p.method_id = pm.method_id "
				+ "LEFT JOIN employees e " + "ON p.employee_id = e.employee_id " + "LEFT JOIN branches b "
				+ "ON o.branch_id = b.branch_id " + "LEFT JOIN order_details od " + "ON o.order_id = od.order_id "
				+ "LEFT JOIN products pr " + "ON od.product_id = pr.product_id " + "LEFT JOIN product_types pt "
				+ "ON pr.item_id = pt.type_id " + "WHERE p.isdeleted = false " + "GROUP BY p.payment_id "
				+ "ORDER BY DATE(o.created_time) DESC, " + "o.created_time ASC, " + "o.token_number ASC";

		return jdbcTemplate.query(sql, new PaymentMapper());
	}

	public List<Payment> findDeletedAll() {

		String sql = "SELECT " + "p.*, " + "o.token_number, " + "pm.name AS pay_method_name, "
				+ "e.name AS employee_name, " + "b.name AS branch_name, " + "o.total_amount, "
				+ "GROUP_CONCAT(pt.name SEPARATOR ', ') AS product_name " + "FROM payments p " + "LEFT JOIN orders o "
				+ "ON p.order_id = o.order_id " + "LEFT JOIN pay_methods pm " + "ON p.method_id = pm.method_id "
				+ "LEFT JOIN employees e " + "ON p.employee_id = e.employee_id " + "LEFT JOIN branches b "
				+ "ON o.branch_id = b.branch_id " + "LEFT JOIN order_details od " + "ON o.order_id = od.order_id "
				+ "LEFT JOIN products pr " + "ON od.product_id = pr.product_id " + "LEFT JOIN product_types pt "
				+ "ON pr.item_id = pt.type_id " + "WHERE p.isdeleted = true " + "GROUP BY p.payment_id "
				+ "ORDER BY DATE(o.created_time) DESC, " + "o.created_time ASC, " + "o.token_number ASC";

		return jdbcTemplate.query(sql, new PaymentMapper());
	}

	public Payment findById(String id) {

		String sql = "SELECT * FROM payments WHERE payment_id = ?";

		return jdbcTemplate.queryForObject(sql, new PaymentMapper(), id);
	}

	public boolean existsByOrderId(String orderId) {

		String sql = """
				SELECT COUNT(*)
				FROM payments
				WHERE order_id = ?
				AND isdeleted = false
				""";

		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, orderId);

		return count != null && count > 0;
	}

	public int save(Payment payment) {

		String sql = "INSERT INTO payments " + "(payment_id, order_id, paid_time, method_id, note, "
				+ "isedited, isdeleted, date, filepath, employee_id) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		return jdbcTemplate.update(sql, UUID.randomUUID().toString(), payment.getOrder_id(), payment.getPaid_time(),
				payment.getMethod_id(), payment.getNote(), payment.isIsedited(), payment.isIsdeleted(),
				payment.getDate(), payment.getFilepath() == null ? "" : payment.getFilepath(),
				payment.getEmployee_id());
	}

	public int edit(String id, Payment payment) {

		String sql = "UPDATE payments SET " + "order_id = ?, " + "paid_time = ?, " + "method_id = ?, " + "note = ?, "
				+ "date = ?, " + "filepath = ?, " + "employee_id = ?, " + "isedited = 1 " + "WHERE payment_id = ?";

		return jdbcTemplate.update(sql, payment.getOrder_id(), payment.getPaid_time(), payment.getMethod_id(),
				payment.getNote(), payment.getDate(), payment.getFilepath(), payment.getEmployee_id(), id);
	}

	public int delete(String id) {

		String sql = "UPDATE payments SET isdeleted = true WHERE payment_id = ?";

		return jdbcTemplate.update(sql, id);
	}

	public int restore(String id) {
		String sql = "UPDATE payments SET isdeleted=false WHERE payment_id=?";
		return jdbcTemplate.update(sql, id);
	}

	public int hardDelete(String id) {
		String sql = "DELETE FROM payments WHERE payment_id=?;";
		return jdbcTemplate.update(sql, id);
	}
}