package cafe.project.KaungSattHein.models;

import java.time.LocalDateTime;
import java.util.List;

public class ProductListModel {
	
	private String product_id, employee_name, type_name, size_code;
	private double price;
	private boolean isedited, isdeleted, is_active;
	private LocalDateTime created_at;
	
	private List<Double> quantity_required;
	private List<String> unit_code, discount_names, ingredient_names;
	
	public ProductListModel() {}
	
	public ProductListModel(String product_id, String employee_name, String type_name, String size_code, double price, boolean isedited, boolean isdeleted, boolean is_active, LocalDateTime created_at, List<Double> quantity_required, List<String> discount_names, List<String> unit_code, List<String> ingredient_names) {
		this.product_id = product_id;
		this.employee_name = employee_name;
		this.type_name = type_name;
		this.size_code = size_code;
		this.price = price;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.is_active = is_active;
		this.created_at = created_at;
		this.quantity_required = quantity_required;
		this.discount_names = discount_names;
		this.ingredient_names = ingredient_names;
		this.unit_code = unit_code;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getSize_code() {
		return size_code;
	}

	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public List<Double> getQuantity_required() {
		return quantity_required;
	}

	public void setQuantity_required(List<Double> quantity_required) {
		this.quantity_required = quantity_required;
	}

	public List<String> getDiscount_names() {
		return discount_names;
	}

	public void setDiscount_names(List<String> discount_names) {
		this.discount_names = discount_names;
	}

	public List<String> getIngredient_names() {
		return ingredient_names;
	}

	public void setIngredient_names(List<String> ingredient_names) {
		this.ingredient_names = ingredient_names;
	}

	public List<String> getUnit_code() {
		return unit_code;
	}

	public void setUnit_code(List<String> unit_code) {
		this.unit_code = unit_code;
	}
	
	

}
