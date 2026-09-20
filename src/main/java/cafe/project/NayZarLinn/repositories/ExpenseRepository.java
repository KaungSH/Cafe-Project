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
		String sql = "INSERT INTO expenses \r\n"
				+ "(expense_id, branch_id, expense_categories, amount, expense_date, description, employee_id, created_at, isdeleted)\r\n"
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
		return this.jdbcTemplate.update(sql,
				entity.getExpense_id(),
				entity.getBranch_id(),
				entity.getExpense_category_id(),
				entity.getAmount(),
				entity.getExpense_date(),
				entity.getDescription(),
				entity.getEmployee_id(),
				entity.getCreated_at(),
				entity.getIsdeleted()
				);
	}

	public int edit(String expense_id, Expense entity) {
		String sql = "UPDATE expenses SET \r\n"
				+ "branch_id=?, expense_categories=?, amount=?, \r\n"
				+ "expense_date=?, description=?,employee_id=?, \r\n"
				+ "created_at=?, isdeleted =? WHERE expense_id=?\r\n";
				
		return this.jdbcTemplate.update(sql,
				entity.getBranch_id(),
				entity.getExpense_category_id(),
				entity.getAmount(),
				entity.getExpense_date(),
				entity.getDescription(),
				entity.getEmployee_id(),
				entity.getCreated_at(),
				entity.getIsdeleted(),
				expense_id);
	}

	public int softDelete(String expense_id) {
		String sql = "UPDATE expenses SET isdeleted = 1 WHERE expense_id = ?";
		return this.jdbcTemplate.update(sql, expense_id);
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
