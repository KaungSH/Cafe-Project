package cafe.project.YinminThiriSoe.models;

import java.time.LocalDateTime;

public class StockImportListModel {
	private String import_id;
	private LocalDateTime imported_at;
	private double total_cost;
	private boolean isdeleted;
	private String supplier_name;
	private String employee_name;
	private String branch_name;

	public StockImportListModel() {
	}

	public StockImportListModel(String import_id, LocalDateTime imported_at, double total_cost, boolean isdeleted,
			String supplier_name, String employee_name, String branch_name) {
		this.import_id = import_id;
		this.imported_at = imported_at;
		this.total_cost = total_cost;
		this.isdeleted = isdeleted;
		this.supplier_name = supplier_name;
		this.employee_name = employee_name;
		this.branch_name = branch_name;
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

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

}