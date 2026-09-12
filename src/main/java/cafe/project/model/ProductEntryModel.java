package cafe.project.model;

import java.util.List;

public class ProductEntryModel {
	
	private String product_id, employee_id, type_id, size_id;
	private double price;
	private boolean is_active;
	private List<String> discount_ids, ingredient_ids;
	
	private List<Double> quantity_required;
	
	public ProductEntryModel() {}
	
	public ProductEntryModel(String product_id, String employee_id, String type_id, String size_id, double price, boolean is_active, List<String> discount_ids, List<String> ingredient_ids, List<Double> quantity_required) {
		this.product_id = product_id;
		this.employee_id = employee_id;
		this.type_id = type_id;
		this.size_id = size_id;
		this.price = price;
		this.is_active = is_active;
		this.discount_ids = discount_ids;
		this.ingredient_ids = ingredient_ids;
		this.quantity_required = quantity_required;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getSize_id() {
		return size_id;
	}

	public void setSize_id(String size_id) {
		this.size_id = size_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public List<String> getDiscount_ids() {
		return discount_ids;
	}

	public void setDiscount_ids(List<String> discount_ids) {
		this.discount_ids = discount_ids;
	}

	public List<String> getIngredient_ids() {
		return ingredient_ids;
	}

	public void setIngredient_ids(List<String> ingredient_ids) {
		this.ingredient_ids = ingredient_ids;
	}

	public List<Double> getQuantity_required() {
		return quantity_required;
	}

	public void setQuantity_required(List<Double> quantity_required) {
		this.quantity_required = quantity_required;
	}
	
	
	

}
