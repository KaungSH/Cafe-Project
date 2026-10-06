package cafe.project.employeemanagement.models;

public class ChangeProfileDto {
	
	//For Validation Checks
	private String email;
	private String password;

	private String employee_id;
	private String photopath;
	private String phone;
	private String employee_name;
	private String address;
	
	
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

	public String getAddress() {
		return address;
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

	public void setAddress(String address) {
		this.address = address;
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
