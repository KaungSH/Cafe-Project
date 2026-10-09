package cafe.project.repositories;


import cafe.project.models.IngredientBatchItemDto;
import cafe.project.models.StockImportListDto;
import cafe.project.repositories.entities.StockImport;
import cafe.project.repositories.mappers.StockImportMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StockImportRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StockImportMapper mapper;

    public StockImportRepository(JdbcTemplate jdbcTemplate, StockImportMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public List<Map<String, Object>> findAllSuppliers() {
        String sql = "SELECT supplier_id, name FROM suppliers WHERE isdeleted = 0 ORDER BY name ASC";
        return jdbcTemplate.queryForList(sql);
    }

    public List<IngredientBatchItemDto> findAllIngredientTypes() {
        String sql = "SELECT it.ingredient_type_id, it.name AS ingredient_name, u.name AS unit_name " +
                     "FROM ingredient_types it " +
                     "JOIN units u ON it.unit_id = u.unit_id " +
                     "WHERE it.isdeleted = 0 ORDER BY it.name ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            IngredientBatchItemDto item = new IngredientBatchItemDto();
            item.setIngredientTypeId(rs.getString("ingredient_type_id"));
            item.setIngredientTypeName(rs.getString("ingredient_name"));
            item.setUname(rs.getString("unit_name"));
            return item;
        });
    }

    public void saveStockImport(StockImport entity) {
        String sql = "INSERT INTO stock_imports (import_id, imported_at, employee_id, branch_id, supplier_id, total_cost, isedited, isdeleted, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 0, 0, NOW())";
        jdbcTemplate.update(sql, entity.getImport_id(), entity.getImported_at(), entity.getEmployee_id(), 
                            entity.getBranch_id(), entity.getSupplier_id(), entity.getTotal_cost());
    }

    public void saveIngredientBatch(String batch_id, IngredientBatchItemDto item, String branch_id, String import_id) {
        String sql = "INSERT INTO ingredient_batches (batch_id, remaining_quantity, manufactured_date, expire_date, branch_id, ingredient_type_id, unit_cost, quantity_ordered, total_import_cost, import_id, isdeleted, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, NOW())";
        jdbcTemplate.update(sql, batch_id, item.getQuantity_ordered(), item.getManufactured_date(), item.getExpire_date(),
        		branch_id, item.getIngredientTypeId(), item.getUnit_cost(), item.getQuantity_ordered(),
                            item.getTotal_import_cost(), import_id);
    }

    public List<StockImportListDto> findAllActive() {
        String sql = "SELECT si.import_id, si.imported_at, s.name AS supplier_name, e.name AS employee_name, b.name AS branch_name, si.total_cost, si.isedited, si.isdeleted " +
                     "FROM stock_imports si " +
                     "JOIN suppliers s ON si.supplier_id = s.supplier_id " +
                     "JOIN employees e ON si.employee_id = e.employee_id " +
                     "JOIN branches b ON si.branch_id = b.branch_id " +
                     "WHERE si.isdeleted = 0 ORDER BY si.created_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            StockImportListDto dto = new StockImportListDto();
            dto.setImport_id(rs.getString("import_id"));
            dto.setImported_at(rs.getTimestamp("imported_at").toLocalDateTime());
            dto.setSupplier_name(rs.getString("supplier_name"));
            dto.setEmployee_name(rs.getString("employee_name"));
            dto.setBranch_name(rs.getString("branch_name"));
            dto.setTotal_cost(rs.getBigDecimal("total_cost"));
            dto.setIsedited(rs.getBoolean("isedited"));
            dto.setIsdeleted(rs.getBoolean("isdeleted"));
            return dto;
        });
    }

    public List<StockImportListDto> findAllDeleted() {
        String sql = "SELECT si.import_id, si.imported_at, s.name AS supplier_name, e.name AS employee_name, b.name AS branch_name, si.total_cost, si.isedited, si.isdeleted " +
                     "FROM stock_imports si " +
                     "JOIN suppliers s ON si.supplier_id = s.supplier_id " +
                     "JOIN employees e ON si.employee_id = e.employee_id " +
                     "JOIN branches b ON si.branch_id = b.branch_id " +
                     "WHERE si.isdeleted = 1 ORDER BY si.created_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            StockImportListDto dto = new StockImportListDto();
            dto.setImport_id(rs.getString("import_id"));
            dto.setImported_at(rs.getTimestamp("imported_at").toLocalDateTime());
            dto.setSupplier_name(rs.getString("supplier_name"));
            dto.setEmployee_name(rs.getString("employee_name"));
            dto.setBranch_name(rs.getString("branch_name"));
            dto.setTotal_cost(rs.getBigDecimal("total_cost"));
            dto.setIsedited(rs.getBoolean("isedited"));
            dto.setIsdeleted(rs.getBoolean("isdeleted"));
            return dto;
        });
    }

    public Optional<StockImport> findById(String import_id) {
        String sql = "SELECT * FROM stock_imports WHERE import_id = ?";
        List<StockImport> list = jdbcTemplate.query(sql, mapper, import_id);
        return list.stream().findFirst();
    }

    public List<IngredientBatchItemDto> findBatchesByImportId(String importId) {
        String sql = "SELECT ib.ingredient_type_id, it.name AS ingredient_type_name, u.name AS unit_name, " +
                     "ib.quantity_ordered, ib.unit_cost, ib.total_import_cost, ib.manufactured_date, ib.expire_date " +
                     "FROM ingredient_batches ib " +
                     "JOIN ingredient_types it ON ib.ingredient_type_id = it.ingredient_type_id " +
                     "JOIN units u ON it.unit_id = u.unit_id " +
                     "WHERE ib.import_id = ?";
                     
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            IngredientBatchItemDto item = new IngredientBatchItemDto();
            item.setIngredientTypeId(rs.getString("ingredient_type_id"));
            item.setIngredientTypeName(rs.getString("ingredient_type_name"));
            item.setUname(rs.getString("unit_name"));
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

    public void deleteBatchesByImportId(String import_id) {
        jdbcTemplate.update("DELETE FROM ingredient_batches WHERE import_id = ?", import_id);
    }

    public void softDelete(String import_id) {
        jdbcTemplate.update("UPDATE stock_imports SET isdeleted = 1 WHERE import_id = ?", import_id);
    }

    public void recover(String import_id) {
        jdbcTemplate.update("UPDATE stock_imports SET isdeleted = 0 WHERE import_id = ?", import_id);
    }

    public void hardDelete(String import_id) {
        jdbcTemplate.update("DELETE FROM ingredient_batches WHERE import_id = ?", import_id);
        jdbcTemplate.update("DELETE FROM stock_imports WHERE import_id = ?", import_id);
    }

    public void recalculateTotalCost(String importId) {
        String sql = "UPDATE stock_imports SET total_cost = " +
                     "COALESCE((SELECT SUM(total_import_cost) FROM ingredient_batches WHERE import_id = ?), 0) " +
                     "WHERE import_id = ?";
        jdbcTemplate.update(sql, importId, importId);
    }
}