package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.repositories.entities.ExpenseCategory;

public class ExpenseCategoryMapper implements RowMapper<ExpenseCategory>{

	@Override
	public ExpenseCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new ExpenseCategory(
				rs.getString("expense_category_id"),
				rs.getString("category_name")
				);
	}

}
