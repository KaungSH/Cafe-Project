package cafe.project.employeemanagement.model;

public class ChangeProfileDto {

	private String employee_id;
	private String photopath;
	private String phone;
	private String employee_name;
	private String salary;
	private String address;
	private String role_id;
	private String branch_id;
	
	public ChangeProfileDto() {}

	public String getEmployee_id() {
		return employee_id;
	}

	public String getPhotopath() {
		return photopath;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public String getSalary() {
		return salary;
	}

	public String getAddress() {
		return address;
	}

	public String getRole_id() {
		return role_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	
	
}
