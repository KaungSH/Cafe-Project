package cafe.project.services;

import org.springframework.stereotype.Service;


import cafe.project.models.WasteLogsEntryDto;
import cafe.project.models.WasteLogsListDto;
import cafe.project.repositories.WasteLogsRepository;
import cafe.project.repositories.entities.IngredientBatch;
import cafe.project.repositories.entities.WasteLogs;

import java.util.List;

@Service
public class WasteLogsService {

	private final WasteLogsRepository wasteLogsRepository;

	public WasteLogsService(WasteLogsRepository wasteLogsRepository) {
		this.wasteLogsRepository = wasteLogsRepository;
	}

	public List<WasteLogsListDto> getAllWasteLogs() {
		return wasteLogsRepository.findAll();
	}

	public WasteLogsEntryDto getWasteLogById(String wasteId) {
		WasteLogs entity = wasteLogsRepository.findById(wasteId);
		return convertToDto(entity);
	}

	public WasteLogsEntryDto getWasteLogByIdAny(String wasteId) {
		WasteLogs entity = wasteLogsRepository.findByIdAny(wasteId);
		return convertToDto(entity);
	}

	public void createWasteLog(WasteLogsEntryDto dto) {
		WasteLogs entity = new WasteLogs();
		entity.setBatch_id(dto.getBatch_id());
		entity.setWaste_reason_id(dto.getWaste_reason_id());
		entity.setQuantity_lost(dto.getQuantity_lost());
		entity.setFinancial_loss(dto.getFinancial_loss());
		entity.setEmployee_id(dto.getEmployee_id());
		entity.setNotes(dto.getNotes());

		wasteLogsRepository.save(entity);
	}

	public void updateWasteLog(WasteLogsEntryDto dto) {
		WasteLogs entity = new WasteLogs();
		entity.setWaste_id(dto.getWaste_id());
		entity.setBatch_id(dto.getBatch_id());
		entity.setWaste_reason_id(dto.getWaste_reason_id());
		entity.setQuantity_lost(dto.getQuantity_lost());
		entity.setFinancial_loss(dto.getFinancial_loss());
		entity.setEmployee_id(dto.getEmployee_id());
		entity.setNotes(dto.getNotes());

		wasteLogsRepository.update(entity);
	}

	public void softDeleteWasteLog(String wasteId) {
		wasteLogsRepository.softDelete(wasteId);
	}

	public List<WasteLogsListDto> getDeletedWasteLogs() {
		return wasteLogsRepository.findDeletedAll();
	}

	public void recoverWasteLog(String wasteId) {
		wasteLogsRepository.recover(wasteId);
	}

	public void hardDeleteWasteLog(String wasteId) {
		wasteLogsRepository.hardDelete(wasteId);
	}

	public List<IngredientBatch> getTodayExpiredBatches() {
		return wasteLogsRepository.findExpiredBatchToday();
	}

	private WasteLogsEntryDto convertToDto(WasteLogs entity) {
		WasteLogsEntryDto dto = new WasteLogsEntryDto();
		dto.setWaste_id(entity.getWaste_id());
		dto.setBatch_id(entity.getBatch_id());
		dto.setWaste_reason_id(entity.getWaste_reason_id());
		dto.setQuantity_lost(entity.getQuantity_lost());
		dto.setFinancial_loss(entity.getFinancial_loss());
		dto.setEmployee_id(entity.getEmployee_id());
		dto.setNotes(entity.getNotes());
		return dto;
	}
}