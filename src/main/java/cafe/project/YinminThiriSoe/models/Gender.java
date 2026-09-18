package cafe.project.YinminThiriSoe.models;

public enum Gender {

	Male("Male"), Female("Female"), NonBinary("Non-Binary"), Other("Other"), PreferNotToSay("Prefer not to say");

	private final String displayName;

	Gender(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	// DB ထဲသိမ်းမယ့် value
	public String getValue() {
		return displayName;
	}
}