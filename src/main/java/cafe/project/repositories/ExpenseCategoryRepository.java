package cafe.project.repositories;

import java.util.List;
import java.util.UUID;

import cafe.project.repositories.entities.ExpenseCategory;
import cafe.project.repositories.mappers.ExpenseCategoryMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExpenseCategoryRepository {

	private final JdbcTemplate jdbcTemplate;

	public ExpenseCategoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ExpenseCategory> findAll() {
		String sql = "SELECT * FROM expense_categories";
		List<ExpenseCategory> entities = this.jdbcTemplate.query(sql, new ExpenseCategoryMapper());
		return entities;
	}

	public ExpenseCategory findById(String expense_category_id) {
		String sql = "SELECT * From expense_categories WHERE expense_category_id =?";
		List<ExpenseCategory> entities = this.jdbcTemplate.query(sql, new ExpenseCategoryMapper(), expense_category_id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int edit(String expense_category_id, ExpenseCategory entity) {
		String sql = "UPDATE expense_categories SET category_name=? WHERE expense_category_id = ?";
		return this.jdbcTemplate.update(sql, entity.getCategory_name(),expense_category_id);
	}

	public int add(ExpenseCategory entity) {
		String sql = "INSERT INTO expense_categories (expense_category_id, category_name) VALUES (?,?)";
		return this.jdbcTemplate.update(sql, UUID.randomUUID().toString(), entity.getCategory_name());
	}

	public int delete(String expense_category_id) {
		String sql = "DELETE FROM expense_categories WHERE expense_category_id = ?";
		return this.jdbcTemplate.update(sql, expense_category_id);
	}
}
