package cafe.project.models;

public class StatusDto {

	private String status_id;
	private String name;
	
	public StatusDto(String status_id, String name) {
		this.status_id = status_id;
		this.name = name;
	}

	public String getStatus_id() {
		return status_id;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
