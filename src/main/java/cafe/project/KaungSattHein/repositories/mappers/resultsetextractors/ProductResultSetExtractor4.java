package cafe.project.KaungSattHein.repositories.mappers.resultsetextractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.KaungSattHein.repositories.entities.Product;

public class ProductResultSetExtractor4 implements ResultSetExtractor<List<Product>>{

	@Override
	public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, Product> productMap = new LinkedHashMap<>();
		
		while(rs.next()) {
			String product_id = rs.getString("p.product_id");
			Product product = productMap.get(product_id);
			if(product == null) {
				product = new Product();
				product.setProduct_id(product_id);
				product.setIngredient_names(new ArrayList<String>());
				product.setQuantity_required(new ArrayList<Double>());
				
			}
			
			String ingredient_name = rs.getString("i.ingredient_name");
			if (ingredient_name != null) {
				product.getIngredient_names().add(ingredient_name);
			}
			
			Double quantity_required = rs.getDouble("i.quantity_required");
			if (quantity_required != null) {
				product.getQuantity_required().add(quantity_required);
			}
			
			productMap.put(product_id, product);
		}
		return new ArrayList<Product>(productMap.values());
	}

}
