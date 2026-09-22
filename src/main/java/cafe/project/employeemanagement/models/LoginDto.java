package cafe.project.employeemanagement.models;

import jakarta.validation.constraints.Email;

public class LoginDto {
	@Email(message = "Invalid email format")
	private String email;
	
	private String employee_id;
	
	private String employee_role_id;
	
	private String employee_role;
	
	private String branch_id;
	
	private String branch_name;
	
	private String employee_status_id;
	
	private String employee_status;

	private String password;

	private String employee_name;


	public LoginDto() {
	}

	public LoginDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public LoginDto(String email, String password, String employee_id, String employee_role, String branch_id, String branch_name, String employee_status, String employee_role_id, String employee_status_id, String employee_name) {
		this.email = email;
		this.password = password;
		this.employee_id = employee_id;
		this.employee_role = employee_role;
		this.branch_id = branch_id;
		this.branch_name = branch_name;
		this.employee_status = employee_status;
		this.employee_role_id = employee_role_id;
		this.employee_status_id = employee_status_id;
		this.employee_name = employee_name;
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

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getEmployee_status() {
		return employee_status;
	}

	public void setEmployee_status(String employee_status) {
		this.employee_status = employee_status;
	}

	public String getEmployee_role_id() {
		return employee_role_id;
	}

	public void setEmployee_role_id(String employee_role_id) {
		this.employee_role_id = employee_role_id;
	}

	public String getEmployee_status_id() {
		return employee_status_id;
	}

	public void setEmployee_status_id(String employee_status_id) {
		this.employee_status_id = employee_status_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	
	

}
