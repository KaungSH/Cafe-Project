package cafe.project.NayZarLinn.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cafe.project.NayZarLinn.models.IngredientBatchDto;
import cafe.project.NayZarLinn.repositories.IngredientBatchRepository;
import cafe.project.NayZarLinn.repositories.entities.IngredientBatch;

@Service
public class IngredientBatchService {

	private final IngredientBatchRepository repo;

	public IngredientBatchService(IngredientBatchRepository repo) {
		this.repo = repo;
	}

	public List<IngredientBatchDto> findAll() {
		this.repo.updateExpiredStatus();
		List<IngredientBatch> entities = this.repo.findAll();
		List<IngredientBatchDto> batches = entities.stream().map(this::toDto).toList();
		return batches;
	}

	public IngredientBatchDto findByBatchId(String batchId) {
		IngredientBatch entity = this.repo.findByBatchId(batchId);
		if (entity == null)
			return null;
		return toDto(entity);
	}

	public List<IngredientBatchDto> findExpired() {
		this.repo.updateExpiredStatus();
		List<IngredientBatch> entities = this.repo.findExpired();
		List<IngredientBatchDto> batches = entities.stream().map(this::toDto).toList();
		return batches;
	}

	public int add(IngredientBatchDto dto) {
		IngredientBatch entity = toEntity(dto);
		return this.repo.save(entity);
	}

	public int edit(String batchId, IngredientBatchDto dto) {
		IngredientBatch entity = toEntity(dto);
		return this.repo.edit(batchId, entity);
	}

	public int delete(String batchId) {
		return this.repo.softDelete(batchId);
	}

	public int softDeleteExpired(String batchId) {
		return this.repo.softDeleteExpired(batchId);
	}

	public void updateExpiredStatus() {
		this.repo.updateExpiredStatus();
	}

	public List<IngredientBatchDto> findDeleted() {
		return this.repo.DeletedList().stream().map(this::toDto).toList();
	}

	public int restore(String batchId) {
		return this.repo.restore(batchId);
	}

	public int hardDelete(String batchId) {
		return this.repo.hardDelete(batchId);
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

	private IngredientBatch toEntity(IngredientBatchDto dto) {

		IngredientBatch entity = new IngredientBatch();

		entity.setBatchId(dto.getBatchId());
		entity.setRemainingQuantity(dto.getRemainingQuantity());
		entity.setManufacturedDate(dto.getManufacturedDate());
		entity.setExpireDate(dto.getExpireDate());
		entity.setBranchId(dto.getBranchId());
		entity.setImportDetailId(dto.getImportDetailId());
		entity.setIngredientTypeId(dto.getIngredientTypeId());
		entity.setUnitCost(dto.getUnitCost());
		entity.setIsExpired(dto.getIsExpired());
		entity.setIsDeleted(dto.getIsDeleted());
		entity.setCreatedAt(dto.getCreatedAt());

		return entity;
	}
}
