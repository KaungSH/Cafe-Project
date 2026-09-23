package cafe.project.YinminThiriSoe.repositories.entities;

import java.time.LocalDateTime;
import java.util.List;

public class StockImport {
	private String import_id;
	private LocalDateTime imported_at;
	private double total_cost;
	private String supplier_id;
	private String employee_id;
	private String branch_id;
	private boolean isdeleted;

	private List<StockImportDetail> stockImportDetails;

	public StockImport() {
	}

	public StockImport(String import_id, LocalDateTime imported_at, double total_cost, String supplier_id,
			String employee_id, String branch_id, boolean isdeleted) {
		this.import_id = import_id;
		this.imported_at = imported_at;
		this.total_cost = total_cost;
		this.supplier_id = supplier_id;
		this.employee_id = employee_id;
		this.branch_id = branch_id;
		this.isdeleted = isdeleted;
	}

	public List<StockImportDetail> getStockImportDetails() {
		return stockImportDetails;
	}

	public void setStockImportDetails(List<StockImportDetail> stockImportDetails) {
		this.stockImportDetails = stockImportDetails;
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

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

}
