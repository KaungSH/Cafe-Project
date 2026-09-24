package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.Expense;

public class ExpenseMapper implements RowMapper<Expense> {

	@Override
	public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {

		LocalDateTime expense_date = rs.getObject("expense_date", LocalDateTime.class);

		LocalDateTime created_at = rs.getObject("created_at", LocalDateTime.class);

		Expense expense = new Expense(rs.getString("expense_id"), rs.getString("branch_id"),
				rs.getString("expense_category_id"), rs.getDouble("amount"), expense_date, rs.getString("description"),
				rs.getString("employee_id"), created_at, rs.getBoolean("isdeleted"));

		expense.setBranch_name(rs.getString("branch_name"));
		expense.setCategory_name(rs.getString("category_name"));

		return expense;
	}

}
