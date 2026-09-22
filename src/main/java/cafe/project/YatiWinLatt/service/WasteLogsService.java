package cafe.project.YatiWinLatt.service;



import org.springframework.stereotype.Service;

import cafe.project.NayZarLinn.models.IngredientBatchDto;
import cafe.project.NayZarLinn.repositories.entities.IngredientBatch;
import cafe.project.YatiWinLatt.models.WasteLogsEntryDto;
import cafe.project.YatiWinLatt.models.WasteLogsListDto;
import cafe.project.YatiWinLatt.repositories.WasteLogsRepository;
import cafe.project.YatiWinLatt.repositories.entities.WasteLogs;

import java.util.List;

@Service
public class WasteLogsService {

  
    private final  WasteLogsRepository wasteLogsRepository;
    
    public WasteLogsService( WasteLogsRepository wasteLogsRepository) {
    	this.wasteLogsRepository=wasteLogsRepository;
    	
    }

    public List<WasteLogsListDto> getAllWasteLogs() {
        return wasteLogsRepository.findAll();
    }
    
    public List<IngredientBatchDto> getTodayExpired() {
    	return wasteLogsRepository.findExpiredBatchToday().stream().map(this::toDto).toList();
    }

    public WasteLogsEntryDto getWasteLogsEntryById(String waste_id) {
        WasteLogs entity = wasteLogsRepository.findById(waste_id);

        WasteLogsEntryDto entryDto = new WasteLogsEntryDto();
        entryDto.setWaste_id(entity.getWaste_id());
        entryDto.setBatch_id(entity.getBatch_id());
        entryDto.setWaste_reason_id(entity.getWaste_reason_id());
        entryDto.setQuantity_lost(entity.getQuantity_lost());
        entryDto.setFinancial_loss(entity.getFinancial_loss());
        entryDto.setEmployee_id(entity.getEmployee_id());
        entryDto.setNotes(entity.getNotes());

        return entryDto;
    }

    public void createWasteLog(WasteLogsEntryDto entryDto) {
        WasteLogs entity = new WasteLogs();
        entity.setBatch_id(entryDto.getBatch_id());
        entity.setWaste_reason_id(entryDto.getWaste_reason_id());
        entity.setQuantity_lost(entryDto.getQuantity_lost());
        entity.setFinancial_loss(entryDto.getFinancial_loss());
        entity.setEmployee_id(entryDto.getEmployee_id());
        entity.setNotes(entryDto.getNotes());

        wasteLogsRepository.save(entity);
    }

    public void updateWasteLog(WasteLogsEntryDto entryDto) {
    	WasteLogs entity = new WasteLogs();
        entity.setWaste_id(entryDto.getWaste_id());
        entity.setBatch_id(entryDto.getBatch_id());
        entity.setWaste_reason_id(entryDto.getWaste_reason_id());
        entity.setQuantity_lost(entryDto.getQuantity_lost());
        entity.setFinancial_loss(entryDto.getFinancial_loss());
        entity.setEmployee_id(entryDto.getEmployee_id());
        entity.setNotes(entryDto.getNotes());

        wasteLogsRepository.update(entity);
    }

    public void deleteWasteLog(String waste_id) {
        wasteLogsRepository.softDelete(waste_id);
    }
    
    private IngredientBatchDto toDto(IngredientBatch entity) {

		IngredientBatchDto dto = new IngredientBatchDto();

		dto.setBatchId(entity.getBatchId());
		dto.setRemainingQuantity(entity.getRemainingQuantity());
		dto.setManufacturedDate(entity.getManufacturedDate());
		dto.setExpireDate(entity.getExpireDate());
		dto.setBranchId(entity.getBranchId());
		dto.setImportDetailId(entity.getImportDetailId());
		dto.setIngredientTypeId(entity.getIngredientTypeId());
		dto.setUnitCost(entity.getUnitCost());
		dto.setIsExpired(entity.getIsExpired());
		dto.setIsDeleted(entity.getIsDeleted());
		dto.setCreatedAt(entity.getCreatedAt());

		return dto;
	}
    
}