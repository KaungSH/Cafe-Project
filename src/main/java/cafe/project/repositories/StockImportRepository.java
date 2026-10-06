package cafe.project.repositories;

import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.jdbc.core.RowMapper; 
import org.springframework.stereotype.Repository;

import cafe.project.models.IngredientBatchItemDto;
import cafe.project.models.StockImportListDto;
import cafe.project.repositories.entities.StockImport;

import java.math.BigDecimal; 
import java.util.List; 
import java.util.Optional;

@Repository 
public class StockImportRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public StockImportRepository(JdbcTemplate jdbcTemplate) {
	    this.jdbcTemplate = jdbcTemplate;
	}
	
	public String generateNextImportId() {
	    String sql = "SELECT import_id FROM stock_imports ORDER BY created_at DESC LIMIT 1";
	    List<String> results = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("import_id"));
	    if (results.isEmpty()) {
	        return "SI-001";
	    }
	    String lastId = results.get(0);
	    int num = Integer.parseInt(lastId.replace("SI-", "")) + 1;
	    return String.format("SI-%03d", num);
	}
	
	public void saveStockImport(StockImport entity) {
	    String sql = "INSERT INTO stock_imports (import_id, imported_at, employee_id, branch_id, supplier_id, total_cost, isedited, isdeleted, created_at) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, 0, 0, NOW())";
	    jdbcTemplate.update(sql, entity.getImport_id(), entity.getImported_at(), entity.getEmployee_id(), 
	                        entity.getBranch_id(), entity.getSupplier_id(), entity.getTotal_cost());
	}
	
	public void saveIngredientBatch(String batchId, IngredientBatchItemDto item, String branchId, String importId) {
	    String sql = "INSERT INTO ingredient_batches (batch_id, remaining_quantity, manufactured_date, expire_date, branch_id, ingredient_type_id, unit_cost, quantity_ordered, total_import_cost, import_id, isdeleted, created_at) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, NOW())";
	    jdbcTemplate.update(sql, batchId, item.getQuantity_ordered(), item.getManufactured_date(), item.getExpire_date(),
	                        branchId, item.getIngredientTypeId(), item.getUnit_cost(), item.getQuantity_ordered(),
	                        item.getTotal_import_cost(), importId);
	}
	
	public List<StockImportListDto> findAllActive() {
	    String sql = "SELECT si.import_id, si.imported_at, s.name AS supplier_name, e.name AS employee_name, b.name AS branch_name, si.total_cost, si.isedited, si.isdeleted " +
	                 "FROM stock_imports si " +
	                 "JOIN suppliers s ON si.supplier_id = s.supplier_id " +
	                 "JOIN employees e ON si.employee_id = e.employee_id " +
	                 "JOIN branches b ON si.branch_id = b.branch_id " +
	                 "WHERE si.isdeleted = 0 ORDER BY si.created_at DESC";
	    return jdbcTemplate.query(sql, listDtoMapper);
	}
	
	public List<StockImportListDto> findAllDeleted() {
	    String sql = "SELECT si.import_id, si.imported_at, s.name AS supplier_name, e.name AS employee_name, b.name AS branch_name, si.total_cost, si.isedited, si.isdeleted " +
	                 "FROM stock_imports si " +
	                 "JOIN suppliers s ON si.supplier_id = s.supplier_id " +
	                 "JOIN employees e ON si.employee_id = e.employee_id " +
	                 "JOIN branches b ON si.branch_id = b.branch_id " +
	                 "WHERE si.isdeleted = 1 ORDER BY si.created_at DESC";
	    return jdbcTemplate.query(sql, listDtoMapper);
	}
	
	public Optional<StockImport> findById(String import_id) {
	    String sql = "SELECT * FROM stock_imports WHERE import_id = ?";
	    List<StockImport> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
	        StockImport entity = new StockImport();
	        entity.setImport_id(rs.getString("import_id"));
	        entity.setImported_at(rs.getTimestamp("imported_at").toLocalDateTime());
	        entity.setEmployee_id(rs.getString("employee_id"));
	        entity.setBranch_id(rs.getString("branch_id"));
	        entity.setSupplier_id(rs.getString("supplier_id"));
	        entity.setTotal_cost(rs.getBigDecimal("total_cost"));
	        entity.setIsedited(rs.getBoolean("isedited"));
	        entity.setIsdeleted(rs.getBoolean("isdeleted"));
	        return entity;
	    }, import_id);
	    return list.stream().findFirst();
	}
	
	public List<IngredientBatchItemDto> findBatchesByImportId(String importId) {
	    String sql = "SELECT ib.ingredient_type_id, it.name AS ingredient_name, u.name AS unit_name, ib.quantity_ordered, ib.unit_cost, ib.total_import_cost, ib.manufactured_date, ib.expire_date " +
	                 "FROM ingredient_batches ib " +
	                 "JOIN ingredient_types it ON ib.ingredient_type_id = it.ingredient_type_id " +
	                 "JOIN units u ON it.unit_id = u.unit_id " +
	                 "WHERE ib.import_id = ?";
	    return jdbcTemplate.query(sql, (rs, rowNum) -> {
	        IngredientBatchItemDto item = new IngredientBatchItemDto();
	        item.setIngredientTypeId(rs.getString("ingredient_type_id"));
	        item.setIngredientTypeName(rs.getString("ingredient_name"));
	        item.setUnit_name(rs.getString("unit_name"));
	        item.setQuantity_ordered(rs.getDouble("quantity_ordered"));
	        item.setUnit_cost(rs.getBigDecimal("unit_cost"));
	        item.setTotal_import_cost(rs.getDouble("total_import_cost"));
	        item.setManufactured_date(rs.getDate("manufactured_date").toLocalDate());
	        item.setExpire_date(rs.getDate("expire_date").toLocalDate());
	        return item;
	    }, importId);
	}
	
	public void updateStockImport(StockImport entity) {
	    String sql = "UPDATE stock_imports SET supplier_id = ?, imported_at = ?, total_cost = ?, isedited = 1 WHERE import_id = ?";
	    jdbcTemplate.update(sql, entity.getSupplier_id(), entity.getImported_at(), entity.getTotal_cost(), entity.getImport_id());
	}
	
	public void deleteBatchesByImportId(String importId) {
	    String sql = "DELETE FROM ingredient_batches WHERE import_id = ?";
	    jdbcTemplate.update(sql, importId);
	}
	
	public void softDelete(String importId) {
	    String sql = "UPDATE stock_imports SET isdeleted = 1 WHERE import_id = ?";
	    jdbcTemplate.update(sql, importId);
	}
	
	public void recover(String importId) {
	    String sql = "UPDATE stock_imports SET isdeleted = 0 WHERE import_id = ?";
	    jdbcTemplate.update(sql, importId);
	}
	
	public void hardDelete(String importId) {
	    String deleteBatches = "DELETE FROM ingredient_batches WHERE import_id = ?";
	    jdbcTemplate.update(deleteBatches, importId);
	    String deleteImport = "DELETE FROM stock_imports WHERE import_id = ?";
	    jdbcTemplate.update(deleteImport, importId);
	}
	
	private final RowMapper<StockImportListDto> listDtoMapper = (rs, rowNum) -> {
	    StockImportListDto dto = new StockImportListDto();
	    dto.setImport_id(rs.getString("import_id"));
	    dto.setImported_at(rs.getTimestamp("imported_at").toLocalDateTime());
	    dto.setSupplier_name(rs.getString("supplier_name"));
	    dto.setEmployee_name(rs.getString("supplier_name"));
	    dto.setBranch_name(rs.getString("branch_name"));
	    dto.setTotal_cost(rs.getBigDecimal("total_cost"));
	    dto.setIsedited(rs.getBoolean("isedited"));
	    dto.setIsdeleted(rs.getBoolean("isdeleted"));
	    return dto;
	};
}