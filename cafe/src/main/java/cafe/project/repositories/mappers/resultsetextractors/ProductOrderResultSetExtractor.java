package cafe.project.repositories.mappers.resultsetextractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.repositories.entities.Product;

public class ProductOrderResultSetExtractor implements ResultSetExtractor<List<Product>> {

	@Override
	public List<Product> extractData(ResultSet rs) throws SQLException {

		Map<String, Product> productMap = new LinkedHashMap<>();

		while (rs.next()) {

			String productId = rs.getString("product_id");

			Product product = productMap.get(productId);

			if (product == null) {

				product = new Product();

				product.setProduct_id(productId);
				product.setEmployee_name(rs.getString("employee_name"));
				product.setType_name(rs.getString("type_name"));
				product.setSize_code(rs.getString("size_code"));
				product.setPrice(rs.getDouble("price"));
				product.setIsedited(rs.getBoolean("isedited"));
				product.setIsdeleted(rs.getBoolean("isdeleted"));
				product.setIs_active(rs.getBoolean("is_active"));

				Timestamp createdAt = rs.getTimestamp("created_at");

				if (createdAt != null) {
					product.setCreated_at(createdAt.toLocalDateTime());
				}

				product.setIngredient_ids(new ArrayList<>());
				product.setIngredient_names(new ArrayList<>());
				product.setQuantity_required(new ArrayList<>());
				product.setUnit_code(new ArrayList<>());

				product.setDiscount_names(new ArrayList<>());
				product.setDiscount_values(new ArrayList<>());

				productMap.put(productId, product);
			}

			String ingredientId = rs.getString("ingredient_type_id");

			if (ingredientId != null && !product.getIngredient_ids().contains(ingredientId)) {

				product.getIngredient_ids().add(ingredientId);

				product.getIngredient_names().add(rs.getString("ingredient_name"));

				product.getUnit_code().add(rs.getString("unit_code"));

				product.getQuantity_required().add(rs.getDouble("quantity_required"));
			}

			String discountName = rs.getString("discount_name");

			if (discountName != null && !product.getDiscount_names().contains(discountName)) {

				product.getDiscount_names().add(discountName);

				double discountValue = rs.getDouble("discount_value");

				if (!rs.wasNull()) {
					product.getDiscount_values().add(discountValue);
				}
			}
		}

		return new ArrayList<>(productMap.values());
	}

}
