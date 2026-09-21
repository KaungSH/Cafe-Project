package cafe.project.KaungSattHein.models;

import java.time.LocalDateTime;
import java.util.List;

public class ProductTypeListModel {
	
	private String type_id, name, description, coverimgpath;
	private double price;
	private LocalDateTime created_at;
	private boolean isdeleted, isedited;
	private String category_name, employee_name;
	private List<ProductListModel> products;
	
	public ProductTypeListModel () {}
	
	public ProductTypeListModel(String type_id, String name, String description, String coverimgpath, double price, LocalDateTime created_at, boolean isdeleted, boolean isedited, String category_name, String employee_name) {
		this.type_id = type_id;
		this.name = name;
		this.description = description;
		this.coverimgpath = coverimgpath;
		this.price = price;
		this.created_at = created_at;
		this.isdeleted = isdeleted;
		this.isedited = isedited;
		this.category_name = category_name;
		this.employee_name = employee_name;
	}
	
	public ProductTypeListModel(String type_id, String name, String description, String coverimgpath, double price, LocalDateTime created_at, boolean isdeleted, boolean isedited, String category_name, String employee_name, List<ProductListModel> products) {
		this.type_id = type_id;
		this.name = name;
		this.description = description;
		this.coverimgpath = coverimgpath;
		this.price = price;
		this.created_at = created_at;
		this.isdeleted = isdeleted;
		this.isedited = isedited;
		this.category_name = category_name;
		this.employee_name = employee_name;
		this.products = products;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverimgpath() {
		return coverimgpath;
	}

	public void setCoverimgpath(String coverimgpath) {
		this.coverimgpath = coverimgpath;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public boolean isIsedited() {
		return isedited;
	}

	public void setIsedited(boolean isedited) {
		this.isedited = isedited;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public List<ProductListModel> getProducts() {
		return products;
	}

	public void setProducts(List<ProductListModel> products) {
		this.products = products;
	}
	
	

}
