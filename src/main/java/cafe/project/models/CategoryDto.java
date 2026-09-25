package cafe.project.models;

import java.time.LocalDateTime;

public class CategoryDto {

	private String category_id;
	private String name;
	private String description;
	private boolean is_active;
	private boolean isedited;
	private boolean isdeleted;
	private LocalDateTime created_at;
	private String employee_id;
	private String branch_id;
	private String branch_name;
	
	public CategoryDto() {}
	
	public CategoryDto(String category_id,String name,String description,
			boolean is_active,boolean isedited,boolean isdeleted,LocalDateTime created_at,String employee_id,String branch_id,String branch_name) {
		this.category_id=category_id;
		this.name=name;
		this.description=description;
		this.is_active=is_active;
		this.isedited=isedited;
		this.isdeleted=isdeleted;
		this.created_at=created_at;
		this.employee_id=employee_id;
		this.branch_id=branch_id;
		this.branch_name=branch_name;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
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

	public boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public boolean getIsedited() {
		return isedited;
	}

	public void setIsedited(boolean isedited) {
		this.isedited = isedited;
	}

	public boolean getIsdeleted() {
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

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

}



