package cafe.project.YinminThiriSoe.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.YinminThiriSoe.repositories.entities.StockImportDetail;
import cafe.project.YinminThiriSoe.repositories.mappers.StockImportDetailMapper;

@Repository
public class StockImportDetailRepository {

	private final JdbcTemplate jdbcTemplate;

	public StockImportDetailRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<StockImportDetail> findByImportId(String importId) {
		String sql = "SELECT * FROM stock_import_details WHERE import_id = ? AND isdeleted = 0";
		return jdbcTemplate.query(sql, new StockImportDetailMapper(), importId);
	}

	public StockImportDetail findById(String import_detailId) {
		String sql = "SELECT * FROM stock_import_details WHERE import_detail_id = ? AND isdeleted = 0";
		return jdbcTemplate.queryForObject(sql, new StockImportDetailMapper(), import_detailId);
	}

	public int add(StockImportDetail entity) {
		String sql = "INSERT INTO stock_import_details "
				+ "(import_detail_id, import_id, ingredient_type_id, quantity_ordered, unit_cost, line_total) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, entity.getImport_detail_id(), entity.getImport_id(),
				entity.getIngredient_type_id(), entity.getQuantity_ordered(), entity.getUnit_cost(),
				entity.getLine_total());
	}

	public int edit(StockImportDetail entity) {
		String sql = "UPDATE stock_import_details SET "
				+ "ingredient_type_id = ?, quantity_ordered = ?, unit_cost = ?, line_total = ? "
				+ "WHERE import_detail_id = ?";
		return jdbcTemplate.update(sql, entity.getIngredient_type_id(), entity.getQuantity_ordered(),
				entity.getUnit_cost(), entity.getLine_total(), entity.getImport_detail_id());
	}

	public int delete(String import_detailId) {
		String sql = "UPDATE stock_import_details SET isdeleted = 1 WHERE import_detail_id = ?";
		return jdbcTemplate.update(sql, import_detailId);
	}
}