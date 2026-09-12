package cafe.project.repository.entity;

import java.time.LocalDateTime;

public class Size {

	private String size_id;
	private String employee_id;
	private String name;
	private String size_code;
	private boolean is_active;
	private boolean isedited;
	private boolean isdeleted;
	private LocalDateTime created_at;

	public Size() {
	}

	public Size(String size_id, String employee_id, String name, String size_code, boolean is_active, boolean isedited,
			boolean isdeleted, LocalDateTime created_at) {
		this.size_id = size_id;
		this.employee_id = employee_id;
		this.name = name;
		this.size_code = size_code;
		this.is_active = is_active;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
	}

	public String getSize_id() {
		return size_id;
	}

	public void setSize_id(String size_id) {
		this.size_id = size_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize_code() {
		return size_code;
	}

	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
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

}
