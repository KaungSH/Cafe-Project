package cafe.project.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockImportListDto {
	private String import_id;
	private LocalDateTime imported_at;
	private String supplier_name;
	private String employee_name;
	private String branch_name;
	private BigDecimal total_cost;
	private boolean isedited;
	private boolean isdeleted;

	public StockImportListDto() {
	}

	public StockImportListDto(String import_id, LocalDateTime imported_at, String supplier_name, String employee_name,
			String branch_name, BigDecimal total_cost, boolean isedited, boolean isdeleted) {
		this.import_id = import_id;
		this.imported_at = imported_at;
		this.supplier_name = supplier_name;
		this.employee_name = employee_name;
		this.branch_name = branch_name;
		this.total_cost = total_cost;
		this.isedited = isedited;
		this.isdeleted = isdeleted;

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

}