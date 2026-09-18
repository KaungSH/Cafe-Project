package cafe.project.KaungSattHein.models;

import java.util.List;

public class ProductTypeEntryModel {
	
	private String type_id, name, description, coverimgpath, coverimgname;
	private double price;
	private String category_id, employee_id;
	
	private List<ProductEntryModel> product;
	
	public ProductTypeEntryModel() {}
	
	public ProductTypeEntryModel(String type_id, String name, String description, String coverimgpath, double price, String category_id, String employee_id) {
		this.type_id = type_id;
		this.name = name;
		this.description = description;
		this.coverimgpath = coverimgpath;
		this.price = price;
		this.category_id = category_id;
		this.employee_id = employee_id;
	}
	
	public ProductTypeEntryModel(String type_id, String name, String description, String coverimgpath, double price, String category_id, String employee_id, String coverimgname) {
		this.type_id = type_id;
		this.name = name;
		this.description = description;
		this.coverimgpath = coverimgpath;
		this.price = price;
		this.category_id = category_id;
		this.employee_id = employee_id;
		this.coverimgname = coverimgname;
	}
	
	public ProductTypeEntryModel(String type_id, String name, String description, String coverimgpath, double price, String category_id, String employee_id, String coverimgname, List<ProductEntryModel> product) {
		this.type_id = type_id;
		this.name = name;
		this.description = description;
		this.coverimgpath = coverimgpath;
		this.price = price;
		this.category_id = category_id;
		this.employee_id = employee_id;
		this.coverimgname = coverimgname;
		this.product = product;
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

	public String getCoverimgname() {
		return coverimgname;
	}

	public void setCoverimgname(String coverimgname) {
		this.coverimgname = coverimgname;
	}

	public List<ProductEntryModel> getProduct() {
		return product;
	}

	public void setProduct(List<ProductEntryModel> product) {
		this.product = product;
	}
	
	

}
