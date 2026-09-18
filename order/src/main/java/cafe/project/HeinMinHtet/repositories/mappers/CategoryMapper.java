package cafe.project.HeinMinHtet.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.HeinMinHtet.repositories.entities.Category;

public class CategoryMapper implements RowMapper<Category> {

	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {

		Category category = new Category();

		category.setCategory_id(rs.getString("category_id"));
		category.setName(rs.getString("name"));
		category.setDescription(rs.getString("description"));
		category.setIs_active(rs.getBoolean("is_active"));
		category.setIsedited(rs.getBoolean("isedited"));
		category.setIsdeleted(rs.getBoolean("isdeleted"));

		category.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());

		category.setEmployee_id(rs.getString("employee_id"));

		return category;
	}
}
