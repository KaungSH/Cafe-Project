package cafe.project.NayZarLinn.repositories.entities;

import java.time.LocalDateTime;

public class Expense {

	private String expense_id;
	private String branch_id;
	private String expense_category_id;
	private double amount;
	private LocalDateTime expense_date;
	private String description;
	private String employee_id;
	private LocalDateTime created_at;
	private Boolean isdeleted;

	public Expense() {
	}

	public Expense(String expense_id, String branch_id, String expense_category_id, double amount,
			LocalDateTime expense_date, String description, String employee_id, LocalDateTime created_at,
			Boolean isdeleted) {
		this.expense_id = expense_id;
		this.branch_id = branch_id;
		this.created_at = created_at;
		this.amount = amount;
		this.expense_date = expense_date;
		this.description = description;
		this.employee_id = employee_id;
		this.created_at = created_at;
		this.isdeleted = isdeleted;
	}

	public String getExpense_id() {
		return expense_id;
	}

	public void setExpense_id(String expense_id) {
		this.expense_id = expense_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getExpense_category_id() {
		return expense_category_id;
	}

	public void setExpense_category_id(String expense_category_id) {
		this.expense_category_id = expense_category_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getExpense_date() {
		return expense_date;
	}

	public void setExpense_date(LocalDateTime expense_date) {
		this.expense_date = expense_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public Boolean getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
}
