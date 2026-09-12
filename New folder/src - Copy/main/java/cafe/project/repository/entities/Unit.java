package cafe.project.repository.entities;

import java.time.LocalDateTime;

public class Unit {

	    private String unit_id;
	    private String employee_id;
	    private String name;
	    private String abbreviation;
	    private boolean is_active;
		private boolean isedited;
		private boolean isdeleted;
		private LocalDateTime created_at;
		
		public Unit() {}
		
		public Unit(String unit_id,String employee_id,String name,String abbreviation,
				boolean is_active,boolean isedited,boolean isdeleted,LocalDateTime created_at) {
			this.unit_id=unit_id;
			this.employee_id=employee_id;
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

		public String getAbbreviation() {
			return abbreviation;
		}

		public void setAbbreviation(String abbreviation) {
			this.abbreviation = abbreviation;
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

	
}
