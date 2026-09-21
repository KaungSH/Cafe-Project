package cafe.project.employeemanagement.model;

public class ChangePasswordDto {

	private String employee_id;
	private String oldPassword;
	private String newPassword;
	
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

	
	
	
}
