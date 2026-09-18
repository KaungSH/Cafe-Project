package cafe.project.YinminThiriSoe.models.employeemanagement;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginModel {
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
	
	private String employee_id;
	
	private String employee_role;

	private String password;

	@NotBlank(message = "Password is required")

	public LoginModel() {
	}

	public LoginModel(String email, String password) {
		this.email = email;
		this.password = password;
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

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_role() {
		return employee_role;
	}

	public void setEmployee_role(String employee_role) {
		this.employee_role = employee_role;
	}

}
