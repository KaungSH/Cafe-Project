package cafe.project.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockImport {
	private String import_id;
	private LocalDateTime imported_at;
	private String employee_id;
	private String branch_id;
	private String supplier_id;
	private BigDecimal total_cost;
	private boolean isedited;
	private boolean isdeleted;
	private LocalDateTime created_at;

	public StockImport() {
	}

	public StockImport(String import_id, LocalDateTime imported_at, String employee_id, String branch_id,
			String supplier_id, BigDecimal total_cost, boolean isedited, boolean isdeleted, LocalDateTime created_at) {
		this.import_id = import_id;
		this.imported_at = imported_at;
		this.employee_id = employee_id;
		this.branch_id = branch_id;
		this.supplier_id = supplier_id;
		this.total_cost = total_cost;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
		;

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

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public BigDecimal getTotal_cost() {
		return total_cost;
	}

	public void setTotal_cost(BigDecimal total_cost) {
		this.total_cost = total_cost;
	}

	public boolean isIsedited() {
		return isedited;
	}

	public void setIsedited(boolean isedited) {
		this.isedited = isedited;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

}