package cafe.project.YinminThiriSoe.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class StockImportEntryModel {
	private String import_id;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // Fixed DateTimeFormat Pattern
	private LocalDateTime imported_at;

	private double total_cost;
	private String supplier_id;
	private String employee_id;
	private String branch_id;

	private List<StockImportDetailEntry> details;
	
	public StockImportEntryModel() {
	}

	public StockImportEntryModel(String import_id, LocalDateTime imported_at, double total_cost, String supplier_id,
			String employee_id, String branch_id) {
		this.import_id = import_id;
		this.imported_at = imported_at;
		this.total_cost = total_cost;
		this.supplier_id = supplier_id;
		this.employee_id = employee_id;
		this.branch_id = branch_id;
	}

	public List<StockImportDetailEntry> getDetails() {
		return details;
	}

	public void setDetails(List<StockImportDetailEntry> details) {
		this.details = details;
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

	public double getTotal_cost() {
		return total_cost;
	}

	public void setTotal_cost(double total_cost) {
		this.total_cost = total_cost;
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

}