package cafe.project.employeemanagement.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChangePasswordDto {

	private String employee_id;
	
	@NotBlank(message = "You must enter a Password.")
	private String oldPassword;
	
	@NotBlank(message = "New Password cannot be empty")
	@Size(min = 8, max = 25, message = "Password must be between 8 and 25 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_]).*$", message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
	private String newPassword;
	
	@NotBlank(message = "You Must Reconfirm new password")
	private String newPassword2;
	
	public ChangePasswordDto(){}

	public String getEmployee_id() {
		return employee_id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}
	
}
