package cafe.project.KaungSattHein.repositories.entities;

import java.time.LocalDateTime;

public class ProductType {
	
	private String type_id, name, description, coverimgpath;
	private double price;
	private LocalDateTime created_at;
	private boolean isdeleted, isedited;
	private String category_id, employee_id;
	
	public ProductType() {}
	
	public ProductType(String type_id, String name, String description, String coverimgpath, double price, LocalDateTime created_at, boolean isdeleted, boolean isedited, String category_id, String employee_id) {
		this.type_id = type_id;
		this.name = name;
		this.description = description;
		this.coverimgpath = coverimgpath;
		this.price = price;
		this.created_at = created_at;
		this.isdeleted = isdeleted;
		this.isedited = isedited;
		this.category_id = category_id;
		this.employee_id = employee_id;
	}
	
	public ProductType(String type_id, String name, String description, String coverimgpath, double price, String category_id, String employee_id) {
		this.type_id = type_id;
		this.name = name;
		this.description = description;
		this.coverimgpath = coverimgpath;
		this.price = price;
		this.category_id = category_id;
		this.employee_id = employee_id;
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

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	
}
