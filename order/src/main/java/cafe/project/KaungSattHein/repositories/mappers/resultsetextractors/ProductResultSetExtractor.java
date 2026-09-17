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

public class ProductResultSetExtractor implements ResultSetExtractor<List<Product>>{

	@Override
	public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, Product> productMap = new LinkedHashMap<>();
		
		while(rs.next()) {
			String product_id = rs.getString("p.product_id");
			Product product = productMap.get(product_id);
			if(product == null) {
				product = new Product(rs.getString("product_id"), rs.getString("employee_id"),rs.getString("type_id"),
				   		rs.getString("size_id"), rs.getDouble("price"),
				   		rs.getBoolean("isedited"),rs.getBoolean("isdeleted"), rs.getBoolean("is_active"), rs.getObject("created_at", LocalDateTime.class), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<Double>());
				
			}
			
			String discount_id = rs.getString("d.discount_id");
			if (discount_id != null) {
				product.getDiscount_ids().add(discount_id);
			}
			
			String ingredient_id = rs.getString("i.ingredient_id");
			if (ingredient_id != null) {
				product.getIngredient_ids().add(ingredient_id);
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
