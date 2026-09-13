package cafe.project.KaungSattHein.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.KaungSattHein.repositories.entities.ProductType;
import cafe.project.KaungSattHein.repositories.mappers.ProductTypeMapper;

@Repository
public class ProductTypeRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public ProductTypeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ProductType> findAll() {
		String sql = "SELECT * FROM product_types WHERE isdeleted = false";
		return jdbcTemplate.query(sql, new ProductTypeMapper());
	}
	
	public ProductType findById(String id) {
		String sql = "SELECT * FROM product_types WHERE isdeleted = false AND type_id = ?";
		return jdbcTemplate.queryForObject(sql, new ProductTypeMapper(), id);
	}
	
	public List<ProductType> findDeletedAll() {
		String sql = "SELECT * FROM product_types WHERE isdeleted = true";
		return jdbcTemplate.query(sql, new ProductTypeMapper());
	}
	
	public ProductType findDeletedById(String id) {
		String sql = "SELECT * FROM product_types WHERE isdeleted = true AND product_id = ?";
		return jdbcTemplate.queryForObject(sql, new ProductTypeMapper(), id);
	}
	
	public int add(ProductType pt) {
		String sql = "INSERT INTO product_types(type_id, name, description, coverimgpath, price, created_at, category_id, isdeleted, isedited, employee_id) VALUES(?, ?, ?, ?, ?, ?, ?, false, false, ?)";
		return jdbcTemplate.update(sql, pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), LocalDateTime.now(), pt.getCategory_id(), pt.getEmployee_id());
	}
	
	public int edit(ProductType pt) {
		String sql = "UPDATE product_types SET name = ? description = ? coverimgpth = ? price = ? category_id = ? isedited = true WHERE type_id = ?";
		return jdbcTemplate.update(sql, pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), pt.getCategory_id(), pt.getType_id());
	}
	
	public int deleted(String id) {
		return jdbcTemplate.update("UPDATE product_types SET isdeleted = true WHERE type = ?", id);
	}
	
	public int recover(String id) {
		return jdbcTemplate.update("UPDATE product_types SET isdeleted = false WHERE type = ?", id);
	}

}
