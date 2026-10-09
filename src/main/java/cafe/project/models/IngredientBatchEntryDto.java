package cafe.project.models;

import java.util.ArrayList;
import java.util.List;

public class IngredientBatchEntryDto {
	private String import_id;
	private List<IngredientBatchItemDto> items = new ArrayList<>();
	private String branch_id;
	public String getImport_id() {
		return import_id;
	}

	public void setImport_id(String import_id) {
		this.import_id = import_id;
	}

	public List<IngredientBatchItemDto> getItems() {
		return items;
	}

	public void setItems(List<IngredientBatchItemDto> items) {
		this.items = items;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	
}
