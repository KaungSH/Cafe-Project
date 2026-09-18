package cafe.project.YinminThiriSoe.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.YinminThiriSoe.repositories.entities.StockImportDetail;

public class StockImportDetailMapper implements RowMapper<StockImportDetail> {
	@Override
	public StockImportDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		StockImportDetail item = new StockImportDetail();
		item.setImport_detail_id(rs.getString("import_detail_id"));
		item.setImport_id(rs.getString("import_id"));
		item.setIngredient_type_id(rs.getString("ingredient_type_id"));
		item.setQuantity_ordered(rs.getDouble("quantity_ordered"));
		item.setUnit_cost(rs.getDouble("unit_cost"));
		item.setLine_total(rs.getDouble("line_total"));
		item.setIsdeleted(rs.getBoolean("isdeleted"));
		return item;
	}

}
