package cafe.project.YatiWinLatt.models;

import jakarta.validation.constraints.NotBlank;

public class SizeEntryDto {
	private String size_id;
	
	@NotBlank(message = "Size Code is required")
	private String size_code;

	@NotBlank(message = "Size Name is required")
	private String name;

	private Boolean is_active = true;

	@NotBlank(message = "Employee selection is required")
	private String employee_id;

	public SizeEntryDto() {
	}

	public SizeEntryDto(String size_id, String size_code, String name, Boolean is_active, String employee_id) {
		this.size_id = size_id;
		this.size_code = size_code;
		this.name = name;
		this.is_active = is_active;
		this.employee_id = employee_id;
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

	public Boolean getIs_active() {
		return is_active;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setSize_id(String size_id) {
		this.size_id = size_id;
	}

	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

}