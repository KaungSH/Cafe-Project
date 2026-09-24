package cafe.project.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cafe.project.repositories.entities.StockImport;
import cafe.project.repositories.mappers.StockImportMapper;

@Repository
public class StockImportRepository {
	private final JdbcTemplate jdbcTemplate;

	public StockImportRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<StockImport> findAll() {
		String sql = "SELECT * FROM stock_imports WHERE isdeleted = false";
		return this.jdbcTemplate.query(sql, new StockImportMapper());
	}

	public List<StockImport> findDeletedAll() {
		String sql = "SELECT * FROM stock_imports WHERE isdeleted = true";
		return this.jdbcTemplate.query(sql, new StockImportMapper());
	}

	public StockImport findById(String import_id) {
		String sql = "SELECT * FROM stock_imports WHERE import_id = ? AND isdeleted = false";
		List<StockImport> entities = this.jdbcTemplate.query(sql, new StockImportMapper(), import_id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public StockImport findDeletedById(String import_id) {
		String sql = "SELECT * FROM stock_imports WHERE import_id = ? AND isdeleted = true";
		List<StockImport> entities = this.jdbcTemplate.query(sql, new StockImportMapper(), import_id);
		return entities.isEmpty() ? null : entities.get(0);
	}

	public int add(StockImport entity) {
		String sql = "INSERT INTO stock_imports (import_id, imported_at, employee_id, branch_id, isdeleted, supplier_id, total_cost) VALUES (?,?,?,?,?,?,?)";
		return this.jdbcTemplate.update(sql, entity.getImport_id(), entity.getImported_at(), entity.getEmployee_id(),
				entity.getBranch_id(), false, entity.getSupplier_id(), entity.getTotal_cost());
	}

	public int edit(StockImport entity) {
		// Removed the extra 'false' parameter that caused the mismatch
		String sql = "UPDATE stock_imports SET imported_at = ?, employee_id = ?, branch_id = ?, supplier_id = ?, total_cost = ? WHERE import_id = ?";
		return this.jdbcTemplate.update(sql, entity.getImported_at(), entity.getEmployee_id(), entity.getBranch_id(),
				entity.getSupplier_id(), entity.getTotal_cost(), entity.getImport_id());
	}

	public int deleted(String import_id) {
		// Fixed syntax error around '?' and set isdeleted to true
		String sql = "UPDATE stock_imports SET isdeleted = true WHERE import_id = ?";
		return this.jdbcTemplate.update(sql, import_id);
	}

	public int recover(String import_id) {
		// Fixed syntax error around '?' and set isdeleted to false
		String sql = "UPDATE stock_imports SET isdeleted = false WHERE import_id = ?";
		return this.jdbcTemplate.update(sql, import_id);
	}

}