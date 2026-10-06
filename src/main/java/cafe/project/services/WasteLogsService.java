package cafe.project.services;



import org.springframework.stereotype.Service;

import cafe.project.models.IngredientBatchDto;
import cafe.project.repositories.entities.IngredientBatch;
import cafe.project.models.WasteLogsEntryDto;
import cafe.project.models.WasteLogsListDto;
import cafe.project.repositories.WasteLogsRepository;
import cafe.project.repositories.entities.WasteLogs;

import java.util.List;

@Service
public class WasteLogsService {

  
    private final  WasteLogsRepository wasteLogsRepository;
    private final BranchService branchService;
    
    public WasteLogsService( WasteLogsRepository wasteLogsRepository, BranchService branchService) {
    	this.wasteLogsRepository=wasteLogsRepository;
    	this.branchService = branchService;
    	
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

		dto.setBatch_id(entity.getBatch_id());
		dto.setRemaining_quantity(entity.getRemaining_quantity());
		dto.setManufactured_date(entity.getManufactured_date());
		dto.setExpire_date(entity.getExpire_date());
		dto.setBranch_name((branchService.findById(entity.getBranch_id())).getName());
		dto.setIngredientType_name(entity.getIngredient_type_id());
		dto.setUnit_cost(entity.getUnit_cost());
		dto.setIsdeleted(entity.getIsdeleted());
		dto.setCreated_at(entity.getCreated_at());
		dto.setQuantity_ordered(entity.getQuantity_ordered());

		return dto;
	}
    
}