package cafe.project.services;

import org.springframework.stereotype.Service;
import cafe.project.models.IngredientBatchItemDto;
import cafe.project.models.StockImportEntryDto;
import cafe.project.models.StockImportListDto;
import cafe.project.repositories.StockImportRepository;
import cafe.project.repositories.entities.StockImport;
import cafe.project.repositories.mappers.StockImportMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StockImportService {
	private final StockImportRepository repository;
	private final StockImportMapper mapper;

	public StockImportService(StockImportRepository repository, StockImportMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public String generateNextImportId() {
		return repository.generateNextImportId();
	}

	public void addStockImport(StockImportEntryDto dto, String loggedInemployee_id, String loggedInbranch_id) {
		dto.setEmployee_id(loggedInemployee_id);
		dto.setBranch_id(loggedInbranch_id);

		List<IngredientBatchItemDto> selectedItems = dto.getItems().stream().filter(IngredientBatchItemDto::isSelected)
				.collect(Collectors.toList());

		BigDecimal totalCost = BigDecimal.ZERO;
		for (IngredientBatchItemDto item : selectedItems) {
			double total = item.getQuantity_ordered() * item.getUnit_cost().doubleValue();
			item.setTotal_import_cost(total);
			totalCost = totalCost.add(BigDecimal.valueOf(total));
		}
		dto.setTotal_cost(totalCost);

		StockImport entity = mapper.toEntity(dto);
		repository.saveStockImport(entity);

		for (IngredientBatchItemDto item : selectedItems) {
			String batchId = "BATCH-" + UUID.randomUUID().toString().substring(0, 8);
			repository.saveIngredientBatch(batchId, item, loggedInbranch_id, dto.getImport_id());
		}
	}

	public List<StockImportListDto> getAllActiveImports() {
		return repository.findAllActive();
	}

	public List<StockImportListDto> getAllDeletedImports() {
		return repository.findAllDeleted();
	}

	public StockImportEntryDto getImportById(String import_id) {
		StockImport entity = repository.findById(import_id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Import ID: " + import_id));
		StockImportEntryDto dto = mapper.toEntryDto(entity);
		dto.setItems(repository.findBatchesByImportId(import_id));
		return dto;
	}

	public void editStockImport(StockImportEntryDto dto) {
		List<IngredientBatchItemDto> selectedItems = dto.getItems().stream().filter(IngredientBatchItemDto::isSelected)
				.collect(Collectors.toList());

		BigDecimal totalCost = BigDecimal.ZERO;
		for (IngredientBatchItemDto item : selectedItems) {
			double total = item.getQuantity_ordered() * item.getUnit_cost().doubleValue();
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
		repository.softDelete(import_id);
	}

	public void recover(String import_id) {
		repository.recover(import_id);
	}

	public void hardDelete(String import_id) {
		repository.hardDelete(import_id);
	}

	public List<IngredientBatchItemDto> getBatchesByImportId(String import_id) {
		return repository.findBatchesByImportId(import_id);
	}
}