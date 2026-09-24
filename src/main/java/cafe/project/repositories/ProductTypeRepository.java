package cafe.project.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.models.ProductEntryModel;
import cafe.project.models.ProductListModel;
import cafe.project.repositories.entities.ProductType;
import cafe.project.repositories.mappers.ProductTypeMapper;
import cafe.project.services.ProductService;

@Repository
public class ProductTypeRepository {
	
	private final JdbcTemplate jdbcTemplate;
	private final ProductService pservice;
	
	public ProductTypeRepository(JdbcTemplate jdbcTemplate, ProductService pservice) {
		this.jdbcTemplate = jdbcTemplate;
		this.pservice = pservice;
	}
	
	public List<ProductType> findAll() {
		String sql = "SELECT * FROM product_types WHERE isdeleted = false ORDER BY created_at DESC";
		return jdbcTemplate.query(sql, new ProductTypeMapper());
	}
	
	public List<ProductType> searchByName(String keyword) {
	    String searchKeyword = "%" + keyword + "%";
	    String sql = "SELECT * FROM product_types WHERE name LIKE ? AND isdeleted = false";
	    return jdbcTemplate.query(sql, new ProductTypeMapper(), searchKeyword);
	}
	
	public List<ProductType> searchDeletedByName(String keyword) {
	    String searchKeyword = "%" + keyword + "%";
		String sql = "SELECT * FROM product_types WHERE name LIKE ? AND isdeleted = true";
		return jdbcTemplate.query(sql, new ProductTypeMapper(), searchKeyword);
	}
	
	public ProductType findById(String id) {
		String sql = "SELECT * FROM product_types WHERE isdeleted = false AND type_id = ?";
		return jdbcTemplate.queryForObject(sql, new ProductTypeMapper(), id);
	}
	
	public ProductType findById2(String id) {
		String sql = "SELECT * FROM product_types WHERE type_id = ?";
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
		int i =  jdbcTemplate.update(sql, pt.getType_id(), pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), LocalDateTime.now(), pt.getCategory_id(), pt.getEmployee_id());
		for(ProductEntryModel product : pt.getProducts()) {
			pservice.add(product, UUID.randomUUID().toString(), pt.getType_id());
		}
		
		return i;
	}
	
	public int edit(ProductType pt) {
		for(ProductEntryModel product : pt.getProducts()) {
			Boolean i = false;
			for(ProductEntryModel product2 : pservice.findByTypeId(pt.getType_id())) {
				if(product.getSize_id().equals(product2.getSize_id())) {
					System.out.println(product.getSize_id() + "   " + product2.getSize_id());
					pservice.permDelete(product2.getProduct_id());
					pservice.add2(product, UUID.randomUUID().toString(), pt.getType_id());
					i = true;
					break;
				}
			}
			if (!i) {
				pservice.add(product, UUID.randomUUID().toString(), pt.getType_id());
			}
		}
		String sql = "UPDATE product_types SET name = ?, description = ?, coverimgpath = ?, price = ?, category_id = ?, isedited = true WHERE type_id = ?";
		return jdbcTemplate.update(sql, pt.getName(), pt.getDescription(), pt.getCoverimgpath(), pt.getPrice(), pt.getCategory_id(), pt.getType_id());
	}
	
	public int deleted(String id) {
		for (ProductListModel product : pservice.findListAllByTypeId(id)) {
			pservice.delete(product.getProduct_id());
		}
		
		return jdbcTemplate.update("UPDATE product_types SET isdeleted = true WHERE type_id = ?", id);
	}
	
	public int recover(String id) {
		for (ProductListModel product : pservice.findListAllByTypeId2(id)) {
			pservice.recover(product.getProduct_id());
		}
		return jdbcTemplate.update("UPDATE product_types SET isdeleted = false WHERE type_id = ?", id);
	}
	
	public int deletedPerm(String id) {
		System.out.println("Found products to delete: " + pservice.findListAllByTypeId2(id));
		for (ProductListModel product : pservice.findListAllByTypeId2(id)) {
			pservice.permDelete(product.getProduct_id());
		}
		String sql = "DELETE FROM product_types WHERE type_id = ?";
		return jdbcTemplate.update(sql,id);
	}

}
