package cafe.project.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeListDto {
	private String employee_id;
	private String name;
	private String email;
	private String phone;
	private BigDecimal salary;
	private String address;
	private LocalDate dob;
	private String photopath;
	private String gender_name;

	private String ESname;
	private String role_name;
	private String branch_name;

	public EmployeeListDto() {
	}

	public EmployeeListDto(String employee_id, String name, String email, String phone, BigDecimal salary,
			String address, LocalDate dob, String photopath, String gender_name, String ESname, String role_name,
			String branch_name) {
		this.employee_id = employee_id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.address = address;
		this.dob = dob;
		this.photopath = photopath;
		this.gender_name = gender_name;
		this.ESname = ESname;
		this.role_name = role_name;
		this.branch_name = branch_name;
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

	public String getPhotopath() {
		return photopath;
	}
	

	public String getGender_name() {
		return gender_name;
	}

	public void setGender_name(String gender_name) {
		this.gender_name = gender_name;
	}

	public String getESname() {
		return ESname;
	}

	public String getRole_name() {
		return role_name;
	}

	public String getBranch_name() {
		return branch_name;
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

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}


	public void setESname(String eSname) {
		ESname = eSname;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

}
