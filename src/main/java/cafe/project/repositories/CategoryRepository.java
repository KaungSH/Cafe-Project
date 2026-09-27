package cafe.project.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.common.repositories.DeleteRecordRepository;
import cafe.project.common.repositories.entities.DeleteRecord;
import cafe.project.repositories.entities.Category;
import cafe.project.repositories.mappers.CategoryMapper;
import cafe.project.repositories.mappers.CategoryMapper2;

@Repository
public class CategoryRepository {

	private final JdbcTemplate jdbcTemplate;
	private final DeleteRecordRepository deleteRecordRepo;

	public CategoryRepository(JdbcTemplate jdbcTemplate, DeleteRecordRepository deleteRecordRepo) {
		this.jdbcTemplate = jdbcTemplate;
		this.deleteRecordRepo = deleteRecordRepo;
	}

	// GET ALL
	public List<Category> findAll() {
		String sql = "SELECT * FROM categories WHERE isdeleted = false AND category_id != 'deleted'";

		return jdbcTemplate.query(sql, new CategoryMapper());
	}

	public List<Category> findAllByRelation(String branch_id) {
		String sql = "SELECT c.*,e.name employee_name , b.name branch_name\r\n" + "FROM categories c \r\n"
				+ "LEFT JOIN employees e ON c.employee_id=e.employee_id     \r\n"
				+ "LEFT JOIN branches b ON c.branches_branch_id = b.branch_id \r\n" + "WHERE  c.isdeleted = false AND category_id != 'deleted' AND branches_branch_id = ?;";

		return jdbcTemplate.query(sql, new CategoryMapper2(), branch_id);
	}
	
	public List<Category> findAllByRelationAdmin() {
		String sql = "SELECT c.*,e.name employee_name , b.name branch_name\r\n" + "FROM categories c \r\n"
				+ "LEFT JOIN employees e ON c.employee_id=e.employee_id     \r\n"
				+ "LEFT JOIN branches b ON c.branches_branch_id = b.branch_id \r\n" + "WHERE  c.isdeleted = false;";

		return jdbcTemplate.query(sql, new CategoryMapper2());
	}

	public List<Category> findDeletedAll(String branch_id) {
		String sql = "SELECT * FROM categories WHERE isdeleted = true AND category_id != 'deleted' AND branches_branch_id = ?";

		return jdbcTemplate.query(sql, new CategoryMapper(), branch_id);
	}

	// GET BY ID
	public Category findById(String id) {

		String sql = " SELECT * FROM categories WHERE category_id = ?";

		return jdbcTemplate.queryForObject(sql, new CategoryMapper(), id);
	}

	// SAVE
	public int save(Category entity) {
		
		System.out.println("Repository Branch ID = " + entity.getBranch_id());

		String sql = "INSERT INTO categories (category_id, name, description, is_active, isedited, isdeleted, employee_id, branches_branch_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		return jdbcTemplate.update(sql, entity.getCategory_id(), entity.getName(), entity.getDescription(),
				entity.getIs_active(), entity.getIsedited(), entity.getIsdeleted(), entity.getEmployee_id(), entity.getBranch_id());
	}

	// UPDATE
	public int edit(String id, Category entity) {

		String sql = "UPDATE categories SET name = ?, description = ?, is_active = ?, isedited = 1 WHERE category_id = ?";

		return jdbcTemplate.update(sql, entity.getName(), entity.getDescription(), entity.getIs_active(), id);
	}

	// SOFT DELETE
	public int delete(String id) {
		recordDelete(id);
		setProductTypes(id);
		String sql = " UPDATE categories SET isdeleted = 1 WHERE category_id = ? ";
		return jdbcTemplate.update(sql, id);
	}

	public int restore(String id) {
		for(DeleteRecord dr : deleteRecordRepo.getByParentId(id)) {
			jdbcTemplate.update("UPDATE product_types SET category_id = ? WHERE category_id = 'deleted' AND type_id = ?", dr.getParent_id(), dr.getChild_id());
		}
		deleteRecordRepo.deleteByParentId(id);
		String sql = "UPDATE categories SET isdeleted = 0 WHERE category_id = ?";

		return jdbcTemplate.update(sql, id);
	}

	public int hardDelete(String id) {
		deleteRecordRepo.deleteByParentId(id);
		String sql = "DELETE FROM categories WHERE category_id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	private int setProductTypes(String category_id) {
		return jdbcTemplate.update("UPDATE product_types SET category_id = 'deleted' WHERE category_id = ?", category_id);
	}
	
	private int recordDelete(String category_id) {
		int i = 0;
		for (String childId : getChildIds(category_id)) {
			DeleteRecord dr = new DeleteRecord();
			dr.setParent_id(category_id); dr.setParent_table_name("categories"); dr.setChild_id(childId); dr.setChild_table_name("product_types");
			deleteRecordRepo.recordDelete(dr);
			i++;
		}
		return i;
	}
	
	private List<String> getChildIds(String category_id) {
		List<String> child_ids = new ArrayList<String>();
		for(DeleteRecord dr : deleteRecordRepo.getChildIds(category_id, "category_id", "product_types", "type_id")) {
			for(String child_id : dr.getChild_ids()) {
				child_ids.add(child_id);
			}
		}
		
		return child_ids;
	}
	
}
