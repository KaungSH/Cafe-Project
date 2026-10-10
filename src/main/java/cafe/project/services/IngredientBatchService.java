package cafe.project.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cafe.project.models.IngredientBatchListDto;
import cafe.project.repositories.IngredientBatchRepository;
import cafe.project.repositories.entities.IngredientBatch;

@Service
public class IngredientBatchService {
	private final IngredientBatchRepository repository;

	public IngredientBatchService(IngredientBatchRepository repository) {
		this.repository = repository;
	}

	public void createBatch(IngredientBatchListDto batch) {
		if (batch == null || batch.getBatchList() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingredient Batch data is required");
		}
		for (IngredientBatch entity : batch.getBatchList()) {
			if (entity.getBatch_id() == null || entity.getBatch_id().isEmpty()) {
				entity.setBatch_id("IB-" + UUID.randomUUID().toString().substring(0, 8));
			}
			repository.add(entity);
		}
	}

	public List<IngredientBatch> getAllBatches() {
		return repository.findAll();
	}

	public IngredientBatch getBatchById(String id) {
		IngredientBatch entity = repository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Batch");
		}
		return entity;
	}

	public void updateBatch(IngredientBatch entity) {
		if (entity == null || entity.getBatch_id() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingredient Batch data is required");
		}
		IngredientBatch existingEntity = repository.findById(entity.getBatch_id());
		if (existingEntity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Batch");
		}
		repository.edit(entity);
	}

	public void softDeleteBatch(String id) {
		IngredientBatch entity = repository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Batch");
		}
		repository.softDelete(id);
	}

	public List<IngredientBatch> getDeletedBatches() {
		return repository.findDeletedAll();
	}

	public void recoverBatch(String id) {
		IngredientBatch entity = repository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Batch");
		}
		repository.recover(id);
	}

	public void hardDeleteBatch(String id) {
		IngredientBatch entity = repository.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Batch");
		}
		repository.hardDelete(id);
	}

	public BigDecimal getAvailableQuantity(String ingredientTypeId, String branchId) {

		return repository.getAvailableQuantity(ingredientTypeId, branchId);
	}

	public void reduceStockFIFO(String ingredientTypeId, String branchId, BigDecimal quantity) {

		repository.reduceStockFIFO(ingredientTypeId, branchId, quantity);
	}
}