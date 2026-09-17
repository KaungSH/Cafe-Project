package cafe.project.YatiWinLatt.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;

public class BranchEntryDto {

	private String branch_id;

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	private String name;

	@NotBlank(message = "Please enter location")
	@Size(max = 255, message = "Location must not above 255")
	private String location;

	@Size(max = 500, message = "Description must not above 500")
	private String description;

	@NotBlank(message = "Please select one status")
	private String branch_status_id;

	@NotNull(message = "Enter opening time")
	private LocalTime opening_time;

	@NotNull(message = "Enter closing time")
	private LocalTime closing_time;

	private boolean isdeleted;

	public BranchEntryDto() {
	}

	public BranchEntryDto(String branch_id, String name, String location, String description, String branch_status_id,
			LocalTime opening_time, LocalTime closing_time, boolean isdeleted) {
		this.branch_id = branch_id;
		this.name = name;
		this.location = location;
		this.description = description;
		this.branch_status_id = branch_status_id;
		this.opening_time = opening_time;
		this.closing_time = closing_time;
		this.isdeleted = isdeleted;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getBranch_status_id() {
		return branch_status_id;
	}

	public LocalTime getOpening_time() {
		return opening_time;
	}

	public LocalTime getClosing_time() {
		return closing_time;
	}

	public boolean Isdeleted() {
		return isdeleted;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBranch_status_id(String branch_status_id) {
		this.branch_status_id = branch_status_id;
	}

	public void setOpening_time(LocalTime opening_time) {
		this.opening_time = opening_time;
	}

	public void setClosing_time(LocalTime closing_time) {
		this.closing_time = closing_time;
	}

}
