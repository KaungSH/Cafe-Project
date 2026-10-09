package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.DiscountProduct;

public class DiscountProductMapper implements RowMapper<DiscountProduct> {

	@Override
	public DiscountProduct mapRow(ResultSet rs, int rowNum) throws SQLException {

		DiscountProduct discountProduct = new DiscountProduct();

		discountProduct.setDiscount_id(rs.getString("discount_id"));

		discountProduct.setDiscount_name(rs.getString("discount_name"));

		discountProduct.setProduct_name(rs.getString("product_name"));
		return discountProduct;
	}
}
