package cafe.project.HeinMinHtet.models;

public enum OrderStatus {

	PENDING("Pending"),
	PREPARING ("Preparing"),
	COMPLETED("Completed"),
	CANCELLED("Cancelled");
	
	private final String displayName;
	OrderStatus(String displayName){
		this.displayName=displayName;
		
	}
	public String getDisplayName() {
		return displayName;
	}
	
	
}
