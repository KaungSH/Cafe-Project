package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.models.DiscountListModel;

public class DiscountListModelMapper implements RowMapper<DiscountListModel> {

	@Override
	public DiscountListModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		DiscountListModel model = new DiscountListModel();
		model.setDiscount_id(rs.getString("discount_id"));
		model.setName(rs.getString("discount_name"));
		model.setDescription(rs.getString("description"));
		model.setDiscount_value(rs.getBigDecimal("discount_value"));

		if (rs.getDate("startdate") != null) {

			model.setStartdate(rs.getDate("startdate").toLocalDate());
		}
		if (rs.getDate("enddate") != null) {

			model.setEnddate(rs.getDate("enddate").toLocalDate());
		}

		model.setIs_active(rs.getBoolean("is_active"));
		model.setPromo_type_name(rs.getString("promo_type_name"));
		model.setAudience_type_name(rs.getString("audience_type_name"));
		model.setEname(rs.getString("employee_name"));

		return model;
	}
}
