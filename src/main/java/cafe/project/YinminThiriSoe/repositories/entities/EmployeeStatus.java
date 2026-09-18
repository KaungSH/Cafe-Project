package cafe.project.YinminThiriSoe.repositories.entities;

public class EmployeeStatus {

	private String employeeStatusId;
	private String name;

	public EmployeeStatus() {
	}

	public EmployeeStatus(String employeeStatusId, String name) {
		this.employeeStatusId = employeeStatusId;
		this.name = name;
	}

	public String getEmployeeStatusId() {
		return employeeStatusId;
	}

	public void setEmployeeStatusId(String employeeStatusId) {
		this.employeeStatusId = employeeStatusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}