package cafe.project.models;

import java.time.LocalDateTime;

public class BranchesStatsDto {

	private String branch_stats_id;
	private String branch_id;
	private int salecount;
	private double saleamount;
	private String month;
	private boolean isedited;
	private boolean isdeleted;
	private LocalDateTime created_at;
	private double employee_cost;
	private String employee_id;
	private String branch_name;
	private String employee_name;

	public BranchesStatsDto() {

	}

	public BranchesStatsDto(String branch_stats_id, String branch_id, int salecount, double saleamount, String month,
			boolean isedited, boolean isdeleted, LocalDateTime created_at, double employee_cost, String employee_id,
			String branch_name, String employee_name) {
		this.branch_stats_id = branch_stats_id;
		this.branch_id = branch_id;
		this.salecount = salecount;
		this.saleamount = saleamount;
		this.month = month;
		this.isdeleted = isdeleted;
		this.isedited = isedited;
		this.created_at = created_at;
		this.employee_cost = employee_cost;
		this.employee_id = employee_id;
		this.branch_name = branch_name;
		this.employee_name = employee_name;
	}

	public String getBranch_stats_id() {
		return branch_stats_id;
	}

	public void setBranch_stats_id(String branch_stats_id) {
		this.branch_stats_id = branch_stats_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public int getSalecount() {
		return salecount;
	}

	public void setSalecount(int salecount) {
		this.salecount = salecount;
	}

	public double getSaleamount() {
		return saleamount;
	}

	public void setSaleamount(double saleamount) {
		this.saleamount = saleamount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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

	public double getEmployee_cost() {
		return employee_cost;
	}

	public void setEmployee_cost(double employee_cost) {
		this.employee_cost = employee_cost;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

}
