package cafe.project.services;

import cafe.project.models.IngredientBatchEntryDto;
import cafe.project.models.IngredientBatchItemDto;
import cafe.project.models.StockImportEntryDto;
import cafe.project.models.StockImportListDto;
import cafe.project.repositories.StockImportRepository;
import cafe.project.repositories.entities.StockImport;
import cafe.project.repositories.mappers.StockImportMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockImportService {

	private final StockImportRepository repository;
	private final StockImportMapper mapper;

	public StockImportService(StockImportRepository repository, StockImportMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public List<Map<String, Object>> getAllSuppliers() {
		return repository.findAllSuppliers();
	}

	public List<IngredientBatchItemDto> getAllIngredientTypes() {
		return repository.findAllIngredientTypes();
	}

	/*
	 * public void addStockImport(StockImportEntryDto dto, String
	 * loggedInEmployeeId, String loggedInBranchId) { String import_id =
	 * UUID.randomUUID().toString(); dto.setImport_id(import_id);
	 * dto.setEmployee_id(loggedInEmployeeId); dto.setBranch_id(loggedInBranchId);
	 * 
	 * 
	 * List<IngredientBatchItemDto> selectedItems = dto.getItems().stream()
	 * .filter(IngredientBatchItemDto::isSelected) .collect(Collectors.toList());
	 * 
	 * BigDecimal totalCost = BigDecimal.ZERO; for (IngredientBatchItemDto item :
	 * selectedItems) { double total = (item.getQuantity_ordered() != null ?
	 * item.getQuantity_ordered() : 0.0) * (item.getUnit_cost() != null ?
	 * item.getUnit_cost().doubleValue() : 0.0); item.setTotal_import_cost(total);
	 * totalCost = totalCost.add(BigDecimal.valueOf(total)); }
	 * dto.setTotal_cost(totalCost);
	 * 
	 * StockImport entity = mapper.toEntity(dto);
	 * repository.saveStockImport(entity);
	 * 
	 * for (IngredientBatchItemDto item : selectedItems) { String batch_id =
	 * "BATCH-" + UUID.randomUUID().toString().substring(0, 8);
	 * repository.saveIngredientBatch(batch_id, item, loggedInBranchId, import_id);
	 * } }
	 */

	public void addStockImport(StockImportEntryDto dto, String loggedInEmployeeId, String loggedInBranchId) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock Import data is required");
		}
		String importId = UUID.randomUUID().toString();
		dto.setImport_id(importId);
		dto.setEmployee_id(loggedInEmployeeId);
		dto.setBranch_id(loggedInBranchId);

		List<IngredientBatchItemDto> selectedItems = dto.getItems().stream().filter(IngredientBatchItemDto::isSelected)
				.collect(Collectors.toList());

		BigDecimal totalCost = BigDecimal.ZERO;
		for (IngredientBatchItemDto item : selectedItems) {
			double qty = item.getQuantity_ordered() != null ? item.getQuantity_ordered() : 0.0;
			double cost = item.getUnit_cost() != null ? item.getUnit_cost().doubleValue() : 0.0;
			double totalBatchCost = qty * cost;

			item.setTotal_import_cost(totalBatchCost);
			totalCost = totalCost.add(BigDecimal.valueOf(totalBatchCost)); // Ingredient Cost များကို ပေါင်းစပ်ခြင်း
		}
		dto.setTotal_cost(totalCost); // Stock Import ၏ Total Cost ထဲ ထည့်ပေးခြင်း

		StockImport entity = mapper.toEntity(dto);
		repository.saveStockImport(entity);

		for (IngredientBatchItemDto item : selectedItems) {
			String batchId = "BATCH-" + UUID.randomUUID().toString().substring(0, 8);
			repository.saveIngredientBatch(batchId, item, loggedInBranchId, importId);
		}
	}

	public List<StockImportListDto> getAllActiveImports() {
		return repository.findAllActive();
	}

	public List<StockImportListDto> getAllDeletedImports() {
		return repository.findAllDeleted();
	}

	public StockImportEntryDto getImportById(String import_id) {
		StockImport entity = repository.findById(import_id).orElse(null);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock Import");
		}
		StockImportEntryDto dto = mapper.toEntryDto(entity);
		dto.setItems(repository.findBatchesByImportId(import_id));
		return dto;
	}

	public void editStockImport(StockImportEntryDto dto) {
		if (dto == null || dto.getImport_id() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock Import data is required");
		}

		StockImport existingEntity = repository.findById(dto.getImport_id()).orElse(null);
		if (existingEntity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock Import");
		}
		List<IngredientBatchItemDto> selectedItems = dto.getItems().stream().filter(IngredientBatchItemDto::isSelected)
				.collect(Collectors.toList());

		BigDecimal totalCost = BigDecimal.ZERO;
		for (IngredientBatchItemDto item : selectedItems) {
			double total = (item.getQuantity_ordered() != null ? item.getQuantity_ordered() : 0.0)
					* (item.getUnit_cost() != null ? item.getUnit_cost().doubleValue() : 0.0);
			item.setTotal_import_cost(total);
			totalCost = totalCost.add(BigDecimal.valueOf(total));
		}
		dto.setTotal_cost(totalCost);

		StockImport entity = mapper.toEntity(dto);
		repository.updateStockImport(entity);

		repository.deleteBatchesByImportId(dto.getImport_id());
		for (IngredientBatchItemDto item : selectedItems) {
			String batchId = "BATCH-" + UUID.randomUUID().toString().substring(0, 8);
			repository.saveIngredientBatch(batchId, item, dto.getBranch_id(), dto.getImport_id());
		}

	}

	public void softDelete(String import_id) {
		StockImport entity = repository.findById(import_id).orElse(null);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock Import");
		}
		repository.softDelete(import_id);
	}

	public void recover(String import_id) {
		StockImport entity = repository.findById(import_id).orElse(null);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock Import");
		}
		repository.recover(import_id);
	}

	public void hardDelete(String import_id) {
		StockImport entity = repository.findById(import_id).orElse(null);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock Import");
		}
		repository.hardDelete(import_id);
	}

	public List<IngredientBatchItemDto> getBatchesByImportId(String import_id) {
		return repository.findBatchesByImportId(import_id);
	}

	// for ingredientBatch
	public void addIngredientBatchesToImport(IngredientBatchEntryDto dto, String loggedInBranchId) {
		if (dto == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingredient Batch data is required");
		}
		List<IngredientBatchItemDto> selectedItems = dto.getItems().stream().filter(IngredientBatchItemDto::isSelected)
				.collect(Collectors.toList());

		for (IngredientBatchItemDto item : selectedItems) {
			double total = (item.getQuantity_ordered() != null ? item.getQuantity_ordered() : 0.0)
					* (item.getUnit_cost() != null ? item.getUnit_cost().doubleValue() : 0.0);
			item.setTotal_import_cost(total);

			String batchId = "BATCH-" + UUID.randomUUID().toString().substring(0, 8);
			repository.saveIngredientBatch(batchId, item, loggedInBranchId, dto.getImport_id());
		}

		repository.recalculateTotalCost(dto.getImport_id());
	}
}