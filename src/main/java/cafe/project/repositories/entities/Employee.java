package cafe.project.repositories.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Employee {

	private String employee_id, name, email, photopath, password, employee_status_id, phone, address, gender, employee_role_id, branch_id, employee_status_name, branch_name, employee_role_name;
	private double salary;
	private LocalDate dob;
	private LocalDateTime created_at;

	public Employee() {
	}

	public Employee(String employee_id, String name, String email, String photopath, String password, String employee_status_id, String phone, double salary, String address, LocalDate dob, String gender, String employee_role_id, String branch_id, LocalDateTime created_at) {
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
		this.gender= gender;
		this.employee_role_id = employee_role_id;
		this.branch_id = branch_id;
		this.created_at = created_at;

	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployee_status_id() {
		return employee_status_id;
	}

	public void setEmployee_status_id(String employee_status_id) {
		this.employee_status_id = employee_status_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmployee_role_id() {
		return employee_role_id;
	}

	public void setEmployee_role_id(String employee_role_id) {
		this.employee_role_id = employee_role_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public String getEmployee_status_name() {
		return employee_status_name;
	}

	public void setEmployee_status_name(String employee_status_name) {
		this.employee_status_name = employee_status_name;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getEmployee_role_name() {
		return employee_role_name;
	}

	public void setEmployee_role_name(String employee_role_name) {
		this.employee_role_name = employee_role_name;
	}
	
	
	

}