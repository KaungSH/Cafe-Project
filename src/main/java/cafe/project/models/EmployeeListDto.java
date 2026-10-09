package cafe.project.models;

import java.time.LocalDate;

public class EmployeeListDto {
	private String employee_id;
	private String name;
	private String email;
	private String phone;
	private double salary;
	private String address;
	private LocalDate dob;
	private String photopath;
	private Gender gender;
	
	private String role_name;
	private String branch_name;
	private String status_name;

	public EmployeeListDto() {
	}

	public EmployeeListDto(String employee_id, String name, String email, String phone, double salary, String address, LocalDate dob, String photopath, Gender gender, String role_name, String branch_name, String status_name) {
		this.employee_id = employee_id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.address = address;
		this.dob = dob;
		this.photopath = photopath;
		this.gender= gender;
		this.role_name = role_name;
		this.branch_name = branch_name;
		this.status_name = status_name;
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

	public double getSalary() {
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public void setSalary(double salary) {
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

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	
	

}
