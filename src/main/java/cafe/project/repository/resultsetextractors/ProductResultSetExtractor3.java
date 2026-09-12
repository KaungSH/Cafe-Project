package cafe.project.repository.resultsetextractors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cafe.project.repository.entities.Product;

public class ProductResultSetExtractor3 implements ResultSetExtractor<List<Product>>{

	@Override
	public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, Product> productMap = new LinkedHashMap<>();
		
		while(rs.next()) {
			String product_id = rs.getString("p.product_id");
			Product product = productMap.get(product_id);
			if(product == null) {
				product = new Product();
				product.setProduct_id(product_id);
				product.setDiscount_ids(new ArrayList<String>());
				product.setDiscount_value(new ArrayList<Double>());
				
			}
			
			String discount_id = rs.getString("d.discount_id");
			if (discount_id != null) {
				product.getDiscount_ids().add(discount_id);
			}
			
			Double discount_value = rs.getDouble("d.discount_value");
			if (discount_value != null) {
				product.getDiscount_value().add(discount_value);
			}
			
			productMap.put(product_id, product);
		}
		return new ArrayList<Product>(productMap.values());
	}

}
