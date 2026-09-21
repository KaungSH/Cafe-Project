package cafe.project.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repositories.entities.Category;
import cafe.project.repositories.mappers.CategoryMapper;
import cafe.project.repositories.mappers.CategoryMapper2;
@Repository
public class CategoryRepository {

	private final JdbcTemplate jdbcTemplate;

	public CategoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// GET ALL
	public List<Category> findAll() {
		String sql = "SELECT * FROM categories WHERE isdeleted = false";

		return jdbcTemplate.query(sql, new CategoryMapper());
	}
	public List<Category> findAllByRelation() {
		String sql = "SELECT c.*,e.name employee_name FROM categories c LEFT JOIN employees e ON c.employee_id=e.employee_id WHERE  isdeleted = false";

		return jdbcTemplate.query(sql, new CategoryMapper2());
	}

	public List<Category> findDeletedAll() {
		String sql = "SELECT * FROM categories WHERE isdeleted = true";

		return jdbcTemplate.query(sql, new CategoryMapper());
	}

	// GET BY ID
	public Category findById(String id) {

		String sql = " SELECT * FROM categories WHERE category_id = ?";

		return jdbcTemplate.queryForObject(sql, new CategoryMapper(), id);
	}

	// SAVE
	public int save(Category entity) {

		String sql = "INSERT INTO categories (category_id, name, description, is_active, isedited, isdeleted, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

		return jdbcTemplate.update(sql, entity.getCategory_id(), entity.getName(), entity.getDescription(),
				entity.getIs_active(), entity.getIsedited(), entity.getIsdeleted(), entity.getEmployee_id());
	}

	// UPDATE
	public int edit(String id, Category entity) {

		String sql = "UPDATE categories SET name = ?, description = ?, is_active = ?, isedited = 1 WHERE category_id = ?";

		return jdbcTemplate.update(sql, entity.getName(), entity.getDescription(), entity.getIs_active(), id);
	}

	// SOFT DELETE
	public int delete(String id) {

		String sql = " UPDATE categories SET isdeleted = 1 WHERE category_id = ? ";

		return jdbcTemplate.update(sql, id);
	}
}
