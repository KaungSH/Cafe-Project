package cafe.project.employeemanagement.models;

public class ChangeProfileDto {
	
	//For Validation Checks
	private String email;
	private String password;

	private String employee_id;
	private String photopath;
	private String phone;
	private String employee_name;
	private String salary;
	private String address;
	private String role_id;
	private String branch_id;
	private String gender_id;
	
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

	public String getGender_id() {
		return gender_id;
	}

	public void setGender_id(String gender_id) {
		this.gender_id = gender_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
