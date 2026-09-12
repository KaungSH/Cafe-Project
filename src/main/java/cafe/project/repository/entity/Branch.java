package cafe.project.repository.entity;

import java.sql.Time;
import java.time.LocalDateTime;

public class Branch {

	private String branch_id;
	private String name;
	private String location;
	private String description;
	private String branch_status_id;
	private Time opening_time;
	private Time closing_time;
	private boolean isdeleted;
	private LocalDateTime created_at;

	private String status_name;

	public Branch() {
	}

	public Branch(String branch_id, String name, String location, String description, String branch_status_id,
			Time opening_time, Time closing_time, boolean isdeleted, LocalDateTime created_at, String status_name) {

		this.branch_id = branch_id;
		this.name = name;
		this.location = location;
		this.description = description;
		this.branch_status_id = branch_status_id;
		this.opening_time = opening_time;
		this.closing_time = closing_time;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
		this.status_name = status_name;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBranch_status_id() {
		return branch_status_id;
	}

	public void setBranch_status_id(String branch_status_id) {
		this.branch_status_id = branch_status_id;
	}

	public Time getOpening_time() {
		return opening_time;
	}

	public void setOpening_time(Time opening_time) {
		this.opening_time = opening_time;
	}

	public Time getClosing_time() {
		return closing_time;
	}

	public void setClosing_time(Time closing_time) {
		this.closing_time = closing_time;
	}

	public boolean Isdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
}
