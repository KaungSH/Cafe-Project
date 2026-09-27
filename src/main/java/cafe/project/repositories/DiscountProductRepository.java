package cafe.project.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repositories.entities.DiscountProduct;
import cafe.project.repositories.mappers.DiscountProductMapper;

@Repository
public class DiscountProductRepository {
	private final JdbcTemplate jdbcTemplate;

	public DiscountProductRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<DiscountProduct> findAll() {

		String sql = "SELECT dp.discount_id, d.name AS discount_name, \r\n"
				+ "GROUP_CONCAT(pt.name SEPARATOR ', ') AS product_name \r\n" + "FROM discounts_products dp \r\n"
				+ "JOIN discounts d \r\n" + "ON dp.discount_id = d.discount_id \r\n" + "JOIN products p \r\n"
				+ "ON dp.product_id = p.product_id \r\n" + "JOIN product_types pt \r\n"
				+ "ON p.item_id = pt.type_id \r\n" + "WHERE d.isdeleted = 0 \r\n" + "AND p.isdeleted = 0 \r\n"
				+ "GROUP BY dp.discount_id, d.name \r\n" + "ORDER BY dp.discount_id;";

		List<DiscountProduct> entities = jdbcTemplate.query(sql, new DiscountProductMapper());

		return entities;
	}

	public boolean exists(String discount_id, String product_id) {

		String sql = "SELECT COUNT(*) FROM discounts_products " + "WHERE discount_id = ? AND product_id = ?";

		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, discount_id, product_id);

		return count != null && count > 0;
	}

	public int save(DiscountProduct dp) {

		String sql = "INSERT INTO discounts_products (discount_id, product_id) VALUES (?, ?);";

		return jdbcTemplate.update(sql, dp.getDiscount_id(), dp.getProduct_id());
	}

	public int deleteByDiscountId(String discount_id) {

		String sql = "DELETE FROM discounts_products WHERE discount_id = ?";

		return jdbcTemplate.update(sql, discount_id);
	}
}
