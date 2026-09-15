package cafe.project.KaungSattHein.repositories.mappers.resultsetextractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.KaungSattHein.repositories.entities.Product;

public class ProductResultSetExtractor2 implements ResultSetExtractor<List<Product>>{

	@Override
	public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, Product> productMap = new LinkedHashMap<>();
		
		while(rs.next()) {
			String product_id = rs.getString("p.product_id");
			Product product = productMap.get(product_id);
			if(product == null) {
				product = new Product(rs.getString("product_id"), rs.getString("employee_name"),rs.getString("type_name"),
				   		rs.getString("size_code"), rs.getDouble("price"),
				   		rs.getBoolean("isedited"),rs.getBoolean("isdeleted"), rs.getBoolean("is_active"), rs.getObject("created_at", LocalDateTime.class), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Double>(), new ArrayList<String>(), new ArrayList<String>());
				
			}
			
			String discount_name = rs.getString("discount_name");
			if (discount_name != null) {
				product.getDiscount_names().add(discount_name);
			}
			
			String discount_value = rs.getString("discount_value");
			if (discount_value != null) {
				product.getDiscount_values().add(discount_value);
			}
			
			String ingredient_name = rs.getString("ingredient_name");
			if (ingredient_name != null) {
				product.getIngredient_names().add(ingredient_name);
			}
			
			String unit_code = rs.getString("unit_code");
			if (unit_code != null) {
				product.getUnit_code().add(unit_code);
			}
			
			Double quantity_required = rs.getDouble("i.quantity_required");
			if (quantity_required != null) {
				product.getQuantity_required().add(quantity_required );
			}
			
			productMap.put(product_id, product);
		}
		return new ArrayList<Product>(productMap.values());
	}

}
