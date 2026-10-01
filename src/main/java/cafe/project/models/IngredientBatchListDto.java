package cafe.project.models;

import java.util.List;

import cafe.project.repositories.entities.IngredientBatch;

public class IngredientBatchListDto {
	
	private List<IngredientBatch> batchList;
	
	public IngredientBatchListDto() {}
	
	public IngredientBatchListDto(List<IngredientBatch> batchList) {
		this.batchList = batchList;
	}

	public List<IngredientBatch> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<IngredientBatch> batchList) {
		this.batchList = batchList;
	}
	
	

}
