package cafe.project.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StockImportEntryDto {
	private String import_id;
	private LocalDateTime imported_at;
	private String supplier_id;
	private String employee_id;
	private String branch_id;
	private BigDecimal total_cost;
	private List<IngredientBatchItemDto> items = new ArrayList<>();

	public StockImportEntryDto() {
	}

	public StockImportEntryDto(String import_id, LocalDateTime imported_at, String supplier_id, String employee_id,
			String branch_id, BigDecimal total_cost) {
		this.import_id = import_id;
		this.imported_at = imported_at;
		this.supplier_id = supplier_id;
		this.employee_id = employee_id;
		this.branch_id = branch_id;
		this.total_cost = total_cost;
	}

	public String getImport_id() {
		return import_id;
	}

	public void setImport_id(String import_id) {
		this.import_id = import_id;
	}

	public LocalDateTime getImported_at() {
		return imported_at;
	}

	public void setImported_at(LocalDateTime imported_at) {
		this.imported_at = imported_at;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public BigDecimal getTotal_cost() {
		return total_cost;
	}

	public void setTotal_cost(BigDecimal total_cost) {
		this.total_cost = total_cost;
	}

	public List<IngredientBatchItemDto> getItems() {
		return items;
	}

	public void setItems(List<IngredientBatchItemDto> items) {
		this.items = items;
	}

}
