package cafe.project.YinminThiriSoe.repositories.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee {

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name + ", email=" + email + ", photoPath=" + photoPath
				+ ", password=" + password + ", employeeStatusId=" + employeeStatusId + ", phone=" + phone + ", salary="
				+ salary + ", address=" + address + ", dob=" + dob + ", genderId=" + genderId + ", employeeRoleId="
				+ employeeRoleId + ", branchId=" + branchId + ", createdAt=" + createdAt + "]";
	}

	private String employeeId;
	private String name;
	private String email;
	private String photoPath;
	private String password;
	private String employeeStatusId;
	private String phone;
	private double salary;
	private String address;
	private LocalDate dob;
	private String genderId;
	private String employeeRoleId;
	private String branchId;
	private LocalDateTime createdAt;

	public Employee() {
	}

	public Employee(String employeeId, String name, String email, String photoPath, String password,
			String employeeStatusId, String phone, double salary, String address, LocalDate dob, String genderId,
			String employeeRoleId, String branchId, LocalDateTime createdAt) {
		this.employeeId = employeeId;
		this.name = name;
		this.email = email;
		this.photoPath = photoPath;
		this.password = password;
		this.employeeStatusId = employeeStatusId;
		this.phone = phone;
		this.salary = salary;
		this.address = address;
		this.dob = dob;
		this.genderId = genderId;
		this.employeeRoleId = employeeRoleId;
		this.branchId = branchId;
		this.createdAt = createdAt;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeStatusId() {
		return employeeStatusId;
	}

	public void setEmployeeStatusId(String employeeStatusId) {
		this.employeeStatusId = employeeStatusId;
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

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getEmployeeRoleId() {
		return employeeRoleId;
	}

	public void setEmployeeRoleId(String employeeRoleId) {
		this.employeeRoleId = employeeRoleId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}