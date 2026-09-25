package cafe.project.repositories.entities;

public class ExpenseCategory {

	private String expense_category_id;
	private String category_name;

	public ExpenseCategory() {
	}

	public ExpenseCategory(String expense_category_id, String category_name) {
		this.expense_category_id = expense_category_id;
		this.category_name = category_name;
	}

	public String getExpense_category_id() {
		return expense_category_id;
	}

	public void setExpense_category_id(String expense_category_id) {
		this.expense_category_id = expense_category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

}
