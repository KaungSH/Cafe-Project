package cafe.project.repositories.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Product {
	
	private String product_id, employee_id, type_id, size_id;
	private double price;
	private boolean isedited, isdeleted, is_active;
	private LocalDateTime created_at;
	private List<String> discount_ids, ingredient_ids;
	
	private List<Double> quantity_required, discount_values;
	private List<String> unit_code, discount_names, ingredient_names;
	private String employee_name, type_name, size_code;
	
	public Product() {}
	
	public Product(String product_id, String employee_id, String type_id, String size_id, double price, boolean isedited, boolean isdeleted, boolean is_active, LocalDateTime created_at, List<String> discount_ids, List<String> ingredient_ids, List<Double> quantity_required) {
		this.product_id = product_id;
		this.employee_id = employee_id;
		this.type_id = type_id;
		this.size_id = size_id;
		this.price = price;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
		this.discount_ids = discount_ids;
		this.ingredient_ids = ingredient_ids;
		this.quantity_required = quantity_required;
		this.is_active = is_active;
	}
	
	public Product(String product_id, String employee_id, String type_id, String size_id, double price, boolean is_active, List<String> discount_ids, List<String> ingredient_ids, List<Double> quantity_required) {
		this.product_id = product_id;
		this.employee_id = employee_id;
		this.type_id = type_id;
		this.size_id = size_id;
		this.price = price;
		this.discount_ids = discount_ids;
		this.ingredient_ids = ingredient_ids;
		this.quantity_required = quantity_required;
		this.is_active = is_active;
	}
	
	public Product(String product_id, String employee_name, String type_name, String size_code, double price, boolean isedited, boolean isdeleted, boolean is_active, LocalDateTime created_at, List<String> discount_names, List<String> ingredient_names, List<Double> quantity_required, List<String> unit_code) {
		this.product_id = product_id;
		this.employee_name = employee_name;
		this.type_name = type_name;
		this.size_code = size_code;
		this.price = price;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
		this.discount_names = discount_names;
		this.ingredient_names = ingredient_names;
		this.quantity_required = quantity_required;
		this.unit_code = unit_code;
		this.is_active = is_active;
	}
	
	public Product(String product_id, String employee_name, String type_name, String size_code, double price, boolean isedited, boolean isdeleted, boolean is_active, LocalDateTime created_at, List<String> discount_names, List<String> ingredient_names, List<Double> quantity_required, List<String> unit_code, List<Double> discount_values) {
		this.product_id = product_id;
		this.employee_name = employee_name;
		this.type_name = type_name;
		this.size_code = size_code;
		this.price = price;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
		this.discount_names = discount_names;
		this.ingredient_names = ingredient_names;
		this.quantity_required = quantity_required;
		this.unit_code = unit_code;
		this.discount_values = discount_values;
		this.is_active = is_active;
	}
	
	public Product(String product_id, String employee_id, String type_id, String size_id, double price, boolean isedited, boolean isdeleted, boolean is_active, LocalDateTime created_at) {
		this.product_id = product_id;
		this.employee_id = employee_id;
		this.type_id = type_id;
		this.size_id = size_id;
		this.price = price;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
		this.is_active = is_active;
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

	public void setQuantity_required(List<Double> quantity_required) {
		this.quantity_required = quantity_required;
	}

	public List<String> getUnit_code() {
		return unit_code;
	}

	public void setUnit_code(List<String> unit_code) {
		this.unit_code = unit_code;
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

	public List<Double> getDiscount_values() {
		return discount_values;
	}

	public void setDiscount_values(List<Double> discount_values) {
		this.discount_values = discount_values;
	}

}
