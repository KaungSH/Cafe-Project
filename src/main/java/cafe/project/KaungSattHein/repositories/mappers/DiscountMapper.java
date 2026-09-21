package cafe.project.KaungSattHein.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.KaungSattHein.repositories.entities.Discount;

public class DiscountMapper implements RowMapper<Discount> {

	@Override
	public Discount mapRow(ResultSet rs, int rowNum) throws SQLException {
		Discount discount = new Discount();
		discount.setDiscount_id(rs.getString("discount_id"));
		discount.setEmployee_id(rs.getString("employee_id"));
		discount.setName(rs.getString("name"));
		discount.setDescription(rs.getString("description"));
		discount.setDiscount_value(rs.getBigDecimal("discount_value"));

		if (rs.getDate("startdate") != null) {

			discount.setStartdate(rs.getDate("startdate").toLocalDate());
		}
		if (rs.getDate("enddate") != null) {
			discount.setEnddate(rs.getDate("enddate").toLocalDate());
		}

		discount.setIsedited(rs.getBoolean("isedited"));
		discount.setIs_active(rs.getBoolean("is_active"));
		discount.setIsdeleted(rs.getBoolean("isdeleted"));

		if (rs.getTimestamp("created_at") != null) {

			discount.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
		}

		discount.setPromo_type_id(rs.getString("promo_type_id"));
		discount.setAudience_type_id(rs.getString("audience_type_id"));

		return discount;
	}
}