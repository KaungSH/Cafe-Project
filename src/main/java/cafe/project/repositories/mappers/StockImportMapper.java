package cafe.project.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cafe.project.models.StockImportEntryDto;
import cafe.project.repositories.entities.StockImport;

@Component
public class StockImportMapper implements RowMapper<StockImport> {

    // 1. DTO to Entity
    public StockImport toEntity(StockImportEntryDto dto) {
        StockImport entity = new StockImport();
        entity.setImport_id(dto.getImport_id());
        entity.setImported_at(dto.getImported_at());
        entity.setEmployee_id(dto.getEmployee_id());
        entity.setBranch_id(dto.getBranch_id());
        entity.setSupplier_id(dto.getSupplier_id());
        entity.setTotal_cost(dto.getTotal_cost());
        return entity;
    }

    // 2. Entity to DTO
    public StockImportEntryDto toEntryDto(StockImport entity) {
        StockImportEntryDto dto = new StockImportEntryDto();
        dto.setImport_id(entity.getImport_id());
        dto.setImported_at(entity.getImported_at());
        dto.setEmployee_id(entity.getEmployee_id());
        dto.setBranch_id(entity.getBranch_id());
        dto.setSupplier_id(entity.getSupplier_id());
        dto.setTotal_cost(entity.getTotal_cost());
        return dto;
    }

    // 3. JDBC RowMapper implementation
    @Override
    public StockImport mapRow(ResultSet rs, int rowNum) throws SQLException {
        StockImport entity = new StockImport();
        entity.setImport_id(rs.getString("import_id"));

        LocalDateTime datefromDb = rs.getObject("imported_at", LocalDateTime.class);
        if (datefromDb != null) {
            entity.setImported_at(datefromDb);
        }
        
        entity.setTotal_cost(rs.getBigDecimal("total_cost"));
        entity.setSupplier_id(rs.getString("supplier_id"));
        entity.setEmployee_id(rs.getString("employee_id"));
        entity.setBranch_id(rs.getString("branch_id"));
        entity.setIsdeleted(rs.getBoolean("isdeleted"));

        return entity;
    }
}