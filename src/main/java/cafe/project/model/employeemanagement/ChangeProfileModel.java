package cafe.project.model.employeemanagement;

public class ChangeProfileModel {
	private String employeeId;
	private String name;
	private String phone;
	private String photoPath;
	
	public ChangeProfileModel() {
	}

	public ChangeProfileModel(String employeeId, String name, String phone, String photoPath) {
		this.employeeId = employeeId;
		this.name = name;
		this.phone = phone;
		this.photoPath = photoPath;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

}
