package cafe.project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.models.DiscountListModel;
import cafe.project.repositories.entities.Discount;
import cafe.project.repositories.mappers.resultsetextractors.DiscountListModelResultSetExtractor;
import cafe.project.repositories.mappers.resultsetextractors.DiscountResultSetExtractor;

@Repository
public class DiscountRepository {

	private final JdbcTemplate jdbcTemplate;

	public DiscountRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int save(Discount discount) {
		String sql = "INSERT INTO discounts (discount_id, employee_id, name, description, "
				+ "discount_value, startdate, enddate, is_active, promo_type_id, audience_type_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, discount.getDiscount_id(), discount.getEmployee_id(), discount.getName(),
				discount.getDescription(), discount.getDiscount_value(), discount.getStartdate(), discount.getEnddate(),
				discount.getIs_active(), discount.getPromo_type_id(), discount.getAudience_type_id());
	}

	public int update(Discount discount) {
		String sql = "UPDATE discounts SET employee_id = ?, name = ?, description = ?, "
				+ "discount_value = ?, startdate = ?, enddate = ?, is_active = ?, "
				+ "isedited = 1, promo_type_id = ?, audience_type_id = ? " + "WHERE discount_id = ? AND isdeleted = 0";
		return jdbcTemplate.update(sql, discount.getEmployee_id(), discount.getName(), discount.getDescription(),
				discount.getDiscount_value(), discount.getStartdate(), discount.getEnddate(), discount.getIs_active(),
				discount.getPromo_type_id(), discount.getAudience_type_id(), discount.getDiscount_id());
	}

	public int softDelete(String id) {
		String sql = "UPDATE discounts SET isdeleted = 1 WHERE discount_id = ?";
		return jdbcTemplate.update(sql, id);
	}

	public List<DiscountListModel> DeletedList() {
		String sql = "SELECT d.discount_id, \r\n" + "d.name AS discount_name, \r\n" + "d.description, \r\n"
				+ "d.discount_value, \r\n" + "d.startdate, \r\n" + "d.enddate, \r\n" + "d.is_active, \r\n"
				+ "pt.type_name AS promo_type_name, \r\n" + "at.type_name AS audience_type_name, \r\n"
				+ "e.name AS employee_name \r\n" + "FROM discounts d \r\n" + "JOIN promo_types pt \r\n"
				+ "ON d.promo_type_id = pt.promo_type_id \r\n" + "JOIN audience_types at \r\n"
				+ "ON d.audience_type_id = at.audience_type_id \r\n" + "LEFT JOIN employees e \r\n"
				+ "ON d.employee_id = e.employee_id \r\n" + "WHERE d.isdeleted = 1 \r\n"
				+ "ORDER BY d.created_at DESC;";
		return jdbcTemplate.query(sql, new DiscountListModelResultSetExtractor());
	}

	public int restore(String id) {
		String sql = "UPDATE discounts SET isdeleted = 0 WHERE discount_id = ?";
		return jdbcTemplate.update(sql, id);
	}

	public int hardDelete(String id) {
		String sql = "DELETE FROM discounts WHERE discount_id=?";
		return jdbcTemplate.update(sql, id);
	}

	public Optional<Discount> findById(String id) {
		String sql = "SELECT * FROM discounts WHERE discount_id = ? AND isdeleted = 0";
		List<Discount> results = jdbcTemplate.query(sql, new DiscountResultSetExtractor(), id);
		return (results != null && !results.isEmpty()) ? Optional.of(results.get(0)) : Optional.empty();
	}

	public List<DiscountListModel> findAllForList() {
		String sql = "SELECT d.discount_id, d.name AS discount_name, d.description, d.discount_value, "
				+ "d.startdate, d.enddate, d.is_active, " + "pt.type_name AS promo_type_name, "
				+ "at.type_name AS audience_type_name, " + "e.name AS employee_name " + "FROM discounts d "
				+ "JOIN promo_types pt ON d.promo_type_id = pt.promo_type_id "
				+ "JOIN audience_types at ON d.audience_type_id = at.audience_type_id "
				+ "LEFT JOIN employees e ON d.employee_id = e.employee_id " + "WHERE d.isdeleted = 0 "
				+ "ORDER BY d.created_at DESC";
		return jdbcTemplate.query(sql, new DiscountListModelResultSetExtractor());
	}
}
