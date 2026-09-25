package cafe.project.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee {

	private String employee_id;
	private String name;
	private String email;
	private String photopath;
	private String password;
	private String employee_status_id;
	private String phone;
	private BigDecimal salary;
	private String address;
	private LocalDate dob;
	private String gender_id;
	private String employee_role_id;
	private String branch_id;
	private LocalDateTime created_at;

	public Employee() {
	}

	public Employee(String employee_id, String name, String email, String photopath, String password,
			String employee_status_id, String phone, BigDecimal salary, String address, LocalDate dob, String gender_id,
			String employee_role_id, String branch_id, LocalDateTime created_at) {
		this.employee_id = employee_id;
		this.name = name;
		this.email = email;
		this.photopath = photopath;
		this.password = password;
		this.employee_status_id = employee_status_id;
		this.phone = phone;
		this.salary = salary;
		this.address = address;
		this.dob = dob;
		this.gender_id = gender_id;
		this.employee_role_id = employee_role_id;
		this.branch_id = branch_id;
		this.created_at = created_at;

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

	public String getPhotopath() {
		return photopath;
	}

	public String getPassword() {
		return password;
	}

	public String getEmployee_status_id() {
		return employee_status_id;
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

	public String getGender_id() {
		return gender_id;
	}

	public String getEmployee_role_id() {
		return employee_role_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
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

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmployee_status_id(String employee_status_id) {
		this.employee_status_id = employee_status_id;
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

	public void setGender_id(String gender_id) {
		this.gender_id = gender_id;
	}

	public void setEmployee_role_id(String employee_role_id) {
		this.employee_role_id = employee_role_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

}