package cafe.project.employeemanagement.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChangeProfileDto {
	//Here Password is only here for security, one can not change password through this
	@NotBlank(message = "Password cannot be empty")
	private String password;
	
	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Invalid email format")
	@Size(max = 200, message = "Email must not exceed 200 characters")
	private String email;
	
	//No Validation Check here, because a validation check here would break the photo uploads
	private String photopath;
	
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^09(\\d{7}|\\d{9})$", message = "Invalid phone number format")
	private String phone;
	
	@NotBlank(message = "Name cannot be empty")
	@Size(max = 45, message = "Name must not exceed 45 characters")
	private String employee_name;
	
	@NotBlank(message = "Address cannot be empty")
	@Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
	@Pattern(regexp = "^[a-zA-Z0-9\\s,.#\\-/]+$", message = "Address contains invalid characters")
	private String address;
	
	private String employee_id;
	
	public ChangeProfileDto() {}
	
	public ChangeProfileDto(String password, String email, String photopath, String phone, String employee_name, String address, String employee_id) {
		this.password = password;
		this.email = email;
		this.photopath = photopath;
		this.phone = phone;
		this.employee_name = employee_name;
		this.address = address;
		this.employee_id = employee_id;
	}

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
