package cafe.project.NayZarLinn.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.NayZarLinn.repositories.entities.Expense;
import cafe.project.NayZarLinn.repositories.mappers.ExpenseMapper;

@Repository
public class ExpenseRepository {

	private final JdbcTemplate jdbcTemplate;

	public ExpenseRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Expense> findAll() {
		String sql = "SELECT e.expense_date As date, e.description, e.created_at, e.amount,\r\n"
				+ "b.Name As brandName,\r\n"
				+ "ec.category_name\r\n"
				+ "FROM expenses e\r\n"
				+ "LEFT JOIN branches b ON e.branch_id = b.branch_id \r\n"
				+ "LEFT JOIN expense_categories ec ON e.expense_category_id = ec.expense_category_id";
		List<Expense> entities = this.jdbcTemplate.query(sql, new ExpenseMapper());
		return entities;
	}

	public Expense findById(String expense_id) {
		String sql = "SELECT e.expense_date As date, e.description, e.created_at, e.amount,\r\n"
				+ "b.Name As brandName,\r\n"
				+ "ec.category_name\r\n"
				+ "FROM expenses e\r\n"
				+ "LEFT JOIN branches b ON e.branch_id = b.branch_id \r\n"
				+ "LEFT JOIN expense_categories ec ON e.expense_category_id = ec.expense_category_id\r\n"
				+ "WHERE e.expense_id = ? AND e.isdeleted = 0";
		List<Expense> entities = this.jdbcTemplate.query(sql, new ExpenseMapper(), expense_id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int save(Expense entity) {
		String sql = "";
		return 0;
	}

	public int edit(String expense_id, Expense entity) {
		String sql = "";
		return 0;
	}

	public int softDelete(String expense_id) {
		String sql = "";
		return 0;
	}

	public List<Expense> DeletedList() {
		String sql = "";
		return null;
	}
	
	public int restore(String expense_id) {
		String sql = "";
		return 0;
	}
	
	public int hardDelete(String expense_id) {
		String sql = "";
		return 0;
	}
}
