package cafe.project.repositories.mappers;


import org.springframework.stereotype.Component;
import cafe.project.models.StockImportEntryDto;
import cafe.project.repositories.entities.StockImport;
@Component public class StockImportMapper {
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
}