package cafe.project.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.models.DiscountCalculationDto;
import cafe.project.repositories.entities.OrderDetails;
import cafe.project.repositories.mappers.OrderDetailsMapper;

@Repository
public class OrderDetailsRepository {

	private final JdbcTemplate jdbcTemplate;

	public OrderDetailsRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<OrderDetails> findAll() {

		String sql = "SELECT od.*,\r\n" + "pt.name AS product_name,\r\n" + "p.price AS price\r\n"
				+ "FROM order_details od\r\n" + "LEFT JOIN products p\r\n" + "ON od.product_id = p.product_id\r\n"
				+ "LEFT JOIN product_types pt\r\n" + "ON p.item_id = pt.type_id;";

		return jdbcTemplate.query(sql, new OrderDetailsMapper());
	}

	public OrderDetails findById(String id) {

		String sql = "SELECT od.*, pt.name AS product_name \r\n" + "FROM order_details od \r\n"
				+ "LEFT JOIN products p ON od.product_id = p.product_id \r\n"
				+ "LEFT JOIN product_types pt ON p.item_id = pt.type_id \r\n" + "WHERE od.order_detail_id = ?";

		return jdbcTemplate.queryForObject(sql, new OrderDetailsMapper(), id);
	}

	public List<OrderDetails> findByOrderId(String orderId) {

		String sql = "SELECT od.*, " + "pt.name AS product_name, " + "p.price AS price " + "FROM order_details od "
				+ "LEFT JOIN products p " + "ON od.product_id = p.product_id " + "LEFT JOIN product_types pt "
				+ "ON p.item_id = pt.type_id " + "WHERE od.order_id = ?";

		return jdbcTemplate.query(sql, new OrderDetailsMapper(), orderId);
	}

	public List<String> findOrderIds() {

		String sql = "SELECT order_id FROM orders WHERE isdeleted = false";

		return jdbcTemplate.queryForList(sql, String.class);
	}

	public int save(OrderDetails entity) {

		String sql = "INSERT INTO order_details\r\n" + "(order_detail_id, product_id, order_id, quantity, remark) \r\n"
				+ "VALUES (?, ?, ?, ?, ?);\r\n";

		return jdbcTemplate.update(sql, UUID.randomUUID().toString(), entity.getProduct_id(), entity.getOrder_id(),
				entity.getQuantity(), entity.getRemark());
	}

	public BigDecimal calculateTotalAmount(String orderId) {

		String sql = "SELECT COALESCE(SUM(p.price * od.quantity), 0) " + "FROM order_details od "
				+ "JOIN products p ON od.product_id = p.product_id " + "WHERE od.order_id = ?";

		return jdbcTemplate.queryForObject(sql, BigDecimal.class, orderId);
	}

	public BigDecimal calculateDiscountAmount(String orderId) {

		String sql = "SELECT " + "od.product_id, " + "p.price, " + "od.quantity, " + "d.discount_value "
				+ "FROM order_details od " + "JOIN products p " + "ON od.product_id = p.product_id "
				+ "JOIN discounts_products dp " + "ON p.product_id = dp.product_id " + "JOIN discounts d "
				+ "ON dp.discount_id = d.discount_id " + "WHERE od.order_id = ? " + "AND d.isdeleted = 0 "
				+ "AND d.is_active = 1 " + "AND CURDATE() BETWEEN d.startdate AND d.enddate";

		List<DiscountCalculationDto> discounts = jdbcTemplate.query(sql, (rs, rowNum) -> {

			DiscountCalculationDto dto = new DiscountCalculationDto();

			dto.setPrice(rs.getBigDecimal("price"));
			dto.setQuantity(rs.getInt("quantity"));
			dto.setDiscountValue(rs.getBigDecimal("discount_value"));

			return dto;

		}, orderId);

		BigDecimal totalDiscount = BigDecimal.ZERO;

		for (DiscountCalculationDto discount : discounts) {

			if (discount.getDiscountValue() == null) {
				continue;
			}

			BigDecimal price = discount.getPrice();

			BigDecimal quantity = BigDecimal.valueOf(discount.getQuantity());

			BigDecimal discountValue = discount.getDiscountValue();

			BigDecimal productSubtotal = price.multiply(quantity);

			BigDecimal discountAmount = productSubtotal.multiply(discountValue).divide(BigDecimal.valueOf(100));

			totalDiscount = totalDiscount.add(discountAmount);
		}

		return totalDiscount;
	}

	public int edit(String id, OrderDetails entity) {

		String sql = "UPDATE order_details SET \r\n" + "product_id = ?,order_id = ?, quantity = ?, remark = ? \r\n"
				+ "WHERE order_detail_id = ?";

		return jdbcTemplate.update(sql, entity.getProduct_id(), entity.getOrder_id(), entity.getQuantity(),
				entity.getRemark(), id);
	}

	public int delete(String id) {

		String sql = "DELETE FROM order_details WHERE order_detail_id = ?";

		return jdbcTemplate.update(sql, id);
	}
}
