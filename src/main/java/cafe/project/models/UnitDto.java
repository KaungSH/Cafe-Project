package cafe.project.models;

import java.time.LocalDateTime;

public class UnitDto{

    private String unit_id;
    private String employee_name;
    private String name;
    private String abbreviation;
    private boolean is_active;
	private boolean isedited;
	private boolean isdeleted;
	private LocalDateTime created_at;
	
	public UnitDto() {}
	
	public UnitDto(String unit_id,String employee_name,String name,String abbreviation,
			boolean is_active,boolean isedited,boolean isdeleted,LocalDateTime created_at) {
		this.unit_id=unit_id;
		this.employee_name=employee_name;
		this.name=name;
		this.abbreviation=abbreviation;
		this.is_active=is_active;
		this.isedited=isedited;
		this.isdeleted=isdeleted;
		this.created_at=created_at;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_id) {
		this.employee_name = employee_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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
