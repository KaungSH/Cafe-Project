package cafe.project.model;

import java.time.LocalDateTime;

public class SizeListDto {
	private String size_id;
	private String size_code;
	private String name;
	private Boolean is_active;
	private String employee_name;
	private LocalDateTime created_at;

	public SizeListDto() {
	}

	public SizeListDto(String size_id, String size_code, String name, Boolean is_active, String employee_name,
			LocalDateTime created_at) {
		this.size_id = size_id;
		this.size_code = size_code;
		this.name = name;
		this.is_active = is_active;
		this.employee_name = employee_name;
		this.created_at = created_at;
	}

	public String getSize_id() {
		return size_id;
	}

	public String getSize_code() {
		return size_code;
	}

	public String getName() {
		return name;
	}

	public void setName() {
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

}