package cafe.project.repositories.entities;

public class WasteReason {
	
	private String waste_reason_id;
	private String reason_name;

	public  WasteReason() {
	}

	public  WasteReason(String waste_reason_id, String reason_name) {
		this.waste_reason_id = waste_reason_id;
		this.reason_name = reason_name;
	}

	public String getWaste_reason_id() {
		return waste_reason_id;
	}

	public String getReason_name() {
		return reason_name;
	}

	public void setWaste_reason_id(String waste_reason_id) {
		this.waste_reason_id = waste_reason_id;
	}

	public void setReason_name(String reason_name) {
		this.reason_name = reason_name;
	}

}
