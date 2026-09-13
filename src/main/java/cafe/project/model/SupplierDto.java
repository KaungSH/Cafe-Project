package cafe.project.model;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class SupplierDto {
	@NotBlank(message="Id is required")
	private String supplier_id;
	@NotBlank(message="Name is required")
	private String name;
	@NotBlank(message="ContactInfo is required")
	private String contact_info;
	
	private boolean isdeleted;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime created_at;

	public SupplierDto() {
	}

	public SupplierDto(String supplier_id, String name, String contact_info, boolean isdeleted,
			LocalDateTime created_at) {
		this.supplier_id = supplier_id;
		this.name = name;
		this.contact_info = contact_info;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact_info() {
		return contact_info;
	}

	public void setContact_info(String contact_info) {
		this.contact_info = contact_info;
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
