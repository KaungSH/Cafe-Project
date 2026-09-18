package cafe.project.YinminThiriSoe.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import cafe.project.YinminThiriSoe.repositories.entities.StockImport;

public class StockImportMapper implements RowMapper<StockImport> {

	@Override
	public StockImport mapRow(ResultSet rs, int rowNum) throws SQLException {
		StockImport entity = new StockImport();
		entity.setImport_id(rs.getString("import_id"));

		LocalDateTime datefromDb = rs.getObject("imported_at", LocalDateTime.class);
		if (datefromDb != null) {
			entity.setImported_at(datefromDb);
		}
		entity.setTotal_cost(rs.getDouble("total_cost"));
		entity.setSupplier_id(rs.getString("supplier_id"));
		entity.setEmployee_id(rs.getString("employee_id"));
		entity.setBranch_id(rs.getString("branch_id"));
		entity.setIsdeleted(rs.getBoolean("isdeleted"));

		return entity;
	}

}
