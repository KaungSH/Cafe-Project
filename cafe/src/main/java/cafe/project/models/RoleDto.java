package cafe.project.models;

import java.time.LocalDateTime;

public class RoleDto {
	private String role_id, name, description;
	private boolean isedited, isdeleted;
	private LocalDateTime created_at;
	
	public RoleDto(String role_id, String name, String description, boolean isedited, boolean isdeleted, LocalDateTime created_at) {
		this.role_id = role_id;
		this.name = name;
		this.description = description;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
	}
	
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isIsedited() {
		return isedited;
	}
	public void setIsedited(boolean isedited) {
		this.isedited = isedited;
	}
	public boolean isIsdeleted() {
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
	
	
}
