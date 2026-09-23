package cafe.project.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmployeeEntryDto {

	private String employee_id;

	@NotBlank(message = "Name cannot be empty")
	@Size(max = 45, message = "Name must not exceed 45 characters")
	private String name;

	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Invalid email format")
	@Size(max = 45, message = "Email must not exceed 45 characters")
	private String email;

	@NotBlank(message = "Password cannot be empty")
	@Size(min = 6, max = 45, message = "Password must be between 6 and 45 characters")
	private String password;

	private String photopath;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[0-9+]{8,15}$", message = "Invalid phone number format")
	private String phone;

	@NotNull(message = "Salary is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
	private BigDecimal salary;

	@NotBlank(message = "Address cannot be empty")
	@Size(max = 45, message = "Address must not exceed 45 characters")
	private String address;

	@NotNull(message = "Date of birth is required")
	@Past(message = "Date of birth must be a past date")
	private LocalDate dob;

	@NotNull(message = "Gender is required")
	private String gender;

	@NotBlank(message = "Employee status is required")
	private String employee_status_id;

	@NotBlank(message = "Role is required")
	private String employee_role_id;

	@NotBlank(message = "Branch is required")
	private String branch_id;

	public EmployeeEntryDto() {
	}

	public EmployeeEntryDto(String employee_id, String name, String email, String password, String photopath,
			String phone, BigDecimal salary, String address, LocalDate dob, String gender, String employee_status_id,
			String employee_role_id, String branch_id) {
		this.employee_id = employee_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.photopath = photopath;
		this.phone = phone;
		this.salary = salary;
		this.address = address;
		this.dob = dob;
		this.gender = gender;
		this.employee_status_id = employee_status_id;
		this.employee_role_id = employee_role_id;
		this.branch_id = branch_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPhotopath() {
		return photopath;
	}

	public String getPhone() {
		return phone;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public String getAddress() {
		return address;
	}

	public LocalDate getDob() {
		return dob;
	}

	public String getGender() {
		return gender;
	}

	public String getEmployee_status_id() {
		return employee_status_id;
	}

	public String getEmployee_role_id() {
		return employee_role_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setEmployee_status_id(String employee_status_id) {
		this.employee_status_id = employee_status_id;
	}

	public void setEmployee_role_id(String employee_role_id) {
		this.employee_role_id = employee_role_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

}
