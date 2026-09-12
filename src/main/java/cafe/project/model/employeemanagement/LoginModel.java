package cafe.project.model.employeemanagement;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginModel {
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

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

}
