package cafe.project.NayZarLinn.repositories;

import java.util.List;
import java.util.UUID;

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
		String sql = "SELECT e.*," + "b.name As branch_name,\r\n" + "ec.category_name\r\n" + "FROM expenses e\r\n"
				+ "LEFT JOIN branches b ON e.branch_id = b.branch_id \r\n"
				+ "LEFT JOIN expense_categories ec ON e.expense_category_id = ec.expense_category_id"
				+ " WHERE e.isdeleted = 0";
		List<Expense> entities = this.jdbcTemplate.query(sql, new ExpenseMapper());
		return entities;
	}

	public Expense findById(String expense_id) {
		String sql = "SELECT e.*,\r\n"
				+ "b.Name As branch_name,\r\n" + "ec.category_name\r\n" + "FROM expenses e\r\n"
				+ "LEFT JOIN branches b ON e.branch_id = b.branch_id \r\n"
				+ "LEFT JOIN expense_categories ec ON e.expense_category_id = ec.expense_category_id\r\n"
				+ "WHERE e.expense_id = ? AND e.isdeleted = 0";
		List<Expense> entities = this.jdbcTemplate.query(sql, new ExpenseMapper(), expense_id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int save(Expense entity) {
		String sql = "INSERT INTO expenses \r\n"
				+ "(expense_id, branch_id, expense_category_id, amount, expense_date, description, employee_id, created_at, isdeleted)\r\n"
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
		return this.jdbcTemplate.update(sql, UUID.randomUUID().toString(), entity.getBranch_id(),
				entity.getExpense_category_id(), entity.getAmount(), entity.getExpense_date(), entity.getDescription(),
				entity.getEmployee_id(), entity.getCreated_at(), 0);
	}

	public int edit(String expense_id, Expense entity) {
		String sql = "UPDATE expenses SET \r\n" + "branch_id=?, expense_category_id=?, amount=?, \r\n"
				+ "expense_date=?, description=?,employee_id=?, \r\n"
				+ "created_at=?, isdeleted =? WHERE expense_id=?\r\n";

		return this.jdbcTemplate.update(sql, entity.getBranch_id(), entity.getExpense_category_id(), entity.getAmount(),
				entity.getExpense_date(), entity.getDescription(), entity.getEmployee_id(), entity.getCreated_at(),
				0, expense_id);
	}

	public int softDelete(String expense_id) {
		String sql = "UPDATE expenses SET isdeleted = 1 WHERE expense_id = ?";
		return this.jdbcTemplate.update(sql, expense_id);
	}

	public List<Expense> DeletedList() {
		String sql = "SELECT e.*,\r\n" + "b.Name As branch_name,\r\n" + "ec.category_name\r\n" + "FROM expenses e\r\n"
				+ "LEFT JOIN branches b ON e.branch_id = b.branch_id \r\n"
				+ "LEFT JOIN expense_categories ec ON e.expense_category_id = ec.expense_category_id\r\n"
				+ "WHERE e.isdeleted = 1";
		return this.jdbcTemplate.query(sql, new ExpenseMapper());
	}

	public int restore(String expense_id) {
		String sql = "UPDATE expenses SET isdeleted = 0 WHERE expense_id=?;";
		return jdbcTemplate.update(sql, expense_id);
	}

	public int hardDelete(String expense_id) {
		String sql = "DELETE FROM expenses WHERE expense_id=?";
		return jdbcTemplate.update(sql, expense_id);
	}
}
