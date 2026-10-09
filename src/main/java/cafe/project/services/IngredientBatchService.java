package cafe.project.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
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
		return repository.findById(id);
	}

	public void updateBatch(IngredientBatch entity) {
		repository.edit(entity);
	}

	public void softDeleteBatch(String id) {
		repository.softDelete(id);
	}

	public List<IngredientBatch> getDeletedBatches() {
		return repository.findDeletedAll();
	}

	public void recoverBatch(String id) {
		repository.recover(id);
	}

	public void hardDeleteBatch(String id) {
		repository.hardDelete(id);
	}
	
	public BigDecimal getAvailableQuantity(
	        String ingredientTypeId,
	        String branchId) {

	    return repository.getAvailableQuantity(
	            ingredientTypeId,
	            branchId
	    );
	}
	
	public void reduceStockFIFO(
	        String ingredientTypeId,
	        String branchId,
	        BigDecimal quantity) {

	    repository.reduceStockFIFO(
	            ingredientTypeId,
	            branchId,
	            quantity
	    );
	}
}