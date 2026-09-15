package cafe.project.KaungSattHein.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.KaungSattHein.repositories.entities.Product;
import cafe.project.KaungSattHein.repositories.mappers.ProductMapper;
import cafe.project.KaungSattHein.repositories.mappers.resultsetextractors.ProductResultSetExtractor;
import cafe.project.KaungSattHein.repositories.mappers.resultsetextractors.ProductResultSetExtractor2;
import cafe.project.KaungSattHein.repositories.mappers.resultsetextractors.ProductResultSetExtractor3;

@Repository
public class ProductRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public ProductRepository (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Product> findAll() {
		String sql = "SELECT * FROM products WHERE isdeleted = false";
		return jdbcTemplate.query(sql, new ProductMapper());
	}
	
	public Product findById(String id) {
		String sql = "SELECT * FROM products WHERE id = ? AND isdeleted = false";
		List<Product> entities = jdbcTemplate.query(sql, new ProductMapper(), id);
		return entities.isEmpty()?null:entities.get(0);
	}
	
	public List<Product> findDetailAll() {
		String sql = "SELECT p.*, d.discount_id, i.ingredient_type_id, i.quantity_required FROM products p LEFT JOIN discounts_products d ON p.product_id = d.product_id LEFT JOIN products_ingredients i ON p.product_id = i.product_id WHERE p.isdeleted = false ORDER BY p.created_at";
		return jdbcTemplate.query(sql, new ProductResultSetExtractor());
	}
	
	public Product findDetailById(String id) {
		String sql = "SELECT p.*, d.discount_id, i.ingredient_type_id, i.quantity_required FROM products p LEFT JOIN discounts_products d ON p.product_id = d.product_id LEFT JOIN products_ingredients i ON p.product_id = i.product_id WHERE p.product_id = ? ORDER BY p.created_at";
		List<Product> entities = jdbcTemplate.query(sql, new ProductResultSetExtractor(), id);
		return entities.isEmpty()?null:entities.get(0);
	}
	
	public List<Product> findListAll() {
		String sql = "SELECT p.*, d.name discount_name, d.discount_value discount_value, i.name ingredient_name, pi.quantity_required, e.name employee_name, pt.name type_name, s.size_code, u.abbreviation unit_code FROM products p LEFT JOIN discounts_products dp ON p.product_id = dp.product_id LEFT JOIN discounts d ON dp.discount_id = d.discount_id LEFT JOIN products_ingredients pi ON p.product_id = pi.product_id LEFT JOIN ingredient_types i ON pi.ingredient_type_id = i.ingredient_type_id LEFT JOIN units u ON i.unit_id = u.unit_id LEFT JOIN product_types pt ON p.item_id = pt.type_id LEFT JOIN employees e ON p.employee_id = e.employee_id LEFT JOIN sizes s ON p.size_id = s.size_id WHERE p.isdeleted = false AND p.is_active = true ORDER BY p.created_at";
		return jdbcTemplate.query(sql, new ProductResultSetExtractor2());
	}
	
	public List<Product> findDeletedAll() {
		String sql = "SELECT p.*, d.name discount_name, i.name ingredient_name, pi.quantity_required, e.name employee_name, pt.name type_name, s.size_code, u.abbreviation unit_code FROM products p LEFT JOIN discounts_products dp ON p.product_id = dp.product_id LEFT JOIN discounts d ON dp.discount_id = d.discount_id LEFT JOIN products_ingredients pi ON p.product_id = pi.product_id LEFT JOIN ingredient_types i ON pi.ingredient_type_id = i.ingredient_type_id LEFT JOIN units u ON i.unit_id = u.unit_id LEFT JOIN product_types pt ON p.item_id = pt.type_id LEFT JOIN employees e ON p.employee_id = e.employee_id LEFT JOIN sizes s ON p.size_id = s.size_id WHERE p.isdeleted = true ORDER BY p.created_at";
		return jdbcTemplate.query(sql, new ProductResultSetExtractor2());
	}
	
	public List<Product> findInactiveAll() {
		String sql = "SELECT p.*, d.name discount_name, i.name ingredient_name, pi.quantity_required, e.name employee_name, pt.name type_name, s.size_code, u.abbreviation unit_code FROM products p LEFT JOIN discounts_products dp ON p.product_id = dp.product_id LEFT JOIN discounts d ON dp.discount_id = d.discount_id LEFT JOIN products_ingredients pi ON p.product_id = pi.product_id LEFT JOIN ingredient_types i ON pi.ingredient_type_id = i.ingredient_type_id LEFT JOIN units u ON i.unit_id = u.unit_id LEFT JOIN product_types pt ON p.item_id = pt.type_id LEFT JOIN employees e ON p.employee_id = e.employee_id LEFT JOIN sizes s ON p.size_id = s.size_id WHERE p.is_active = false ORDER BY p.created_at";
		return jdbcTemplate.query(sql, new ProductResultSetExtractor2());
	}
	
	public List<Product> findActiveAll() {
		String sql = "SELECT p.*, d.discount_id, i.ingredient_type_id, i.quantity_required FROM products p LEFT JOIN discounts_products d ON p.product_id = d.product_id LEFT JOIN products_ingredients i ON p.product_id = i.product_id WHERE p.isdeleted = false AND p.is_active = true ORDER BY p.created_at";
		return jdbcTemplate.query(sql, new ProductResultSetExtractor());
	}
	
	public Product findListById(String id) {
		String sql = "SELECT p.*, d.name discount_name, i.name ingredient_name, pi.quantity_required, e.name employee_name, pt.name type_name, s.size_code, u.abbreviation unit_code FROM products p LEFT JOIN discounts_products dp ON p.product_id = dp.product_id LEFT JOIN discounts d ON dp.discount_id = d.discount_id LEFT JOIN products_ingredients pi ON p.product_id = pi.product_id LEFT JOIN ingredient_types i ON pi.ingredient_type_id = i.ingredient_type_id LEFT JOIN units u ON i.unit_id = u.unit_id LEFT JOIN product_types pt ON p.item_id = pt.type_id LEFT JOIN employees e ON p.employee_id = e.employee_id LEFT JOIN sizes s ON p.size_id = s.size_id WHERE p.isdeleted = false AND p.is_active = true AND p.product_id = ? ORDER BY p.created_at";
		List<Product> entities = jdbcTemplate.query(sql, new ProductResultSetExtractor2(), id);
		return entities.isEmpty()?null:entities.get(0);
	}
	
	public Product findDiscountById(String id) {
		String sql = "SELECT p.product_id, d.discount_id, d.discount_name, d.discount_value FROM products p LEFT JOIN discounts_products dp ON p.product_id = dp.product_id LEFT JOIN discounts d ON dp.discount_id = d.discount_id WHERE p.product_id = ?";
		List<Product> entities = jdbcTemplate.query(sql, new ProductResultSetExtractor3(), id);
		return entities.isEmpty()?null:entities.get(0);
	}
	
	public Product findQuantityRequiredById(String id) {
		String sql = "SELECT p.product_id, it.name ingredient_type_name, i.quantity_required, FROM products p LEFT JOIN products_ingredients i ON p.product_id = i.product_id LEFT JOIN ingredient_types it on it.ingredient_type_id = i.ingredient_type_id WHERE p.product_id = ?";
		List<Product> entities = jdbcTemplate.query(sql, new ProductResultSetExtractor3(), id);
		return entities.isEmpty()?null:entities.get(0);
	}
	
	public int add(Product product) {
		String sql = "INSERT INTO products(product_id, employee_id, item_id, size_id, price, isedited, isdeleted, is_active, created_at) VALUES(?, ?, ?, ?, ?, false, false, ?, ?);";
		jdbcTemplate.update(sql, product.getProduct_id(), product.getEmployee_id(), product.getType_id(), product.getSize_id(), product.getPrice(), product.isIs_active(), LocalDateTime.now());
		return addProducts_ingredients(product.getProduct_id(), product.getIngredient_ids(), product.getQuantity_required());
	}
	
	private int addProducts_ingredients(String product_id, List<String> ingredient_ids, List<Double> quantity_required) {
		int i = 0;
		for(String in : ingredient_ids) {
			i += jdbcTemplate.update("INSERT INTO products_ingredients(product_id, discount_id) VALUES (?, ?);", product_id, in);
		}
		for(Double q : quantity_required) {
			i += jdbcTemplate.update("INSERT INTO products_ingredients(quantity) VALUES (?) WHERE product_id = ?;", q, product_id);
		}
		return i;
	}
	
	public int delete(String id) {
		String sql = "UPDATE products SET isdeleted = true WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	public int recover(String id) {
		String sql = "UPDATE products SET isdeleted = false WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	private int deleteProducts_ingredients(String id) {
		return jdbcTemplate.update("DELETE FROM products_ingredients WHERE product_id = ?", id);
	}
	
	public int edit(Product product) {
		String sql = "UPDATE products SET price = ?, isedited = true is_active = ?, WHERE product_id = ?;";
		jdbcTemplate.update(sql, product.getPrice(), product.isIs_active(), product.getProduct_id());
		deleteProducts_ingredients(product.getProduct_id());
		return addProducts_ingredients(product.getProduct_id(), product.getIngredient_ids(), product.getQuantity_required());
	}

}
