package cafe.project.repository.entities;

import java.time.LocalDateTime;

public class Supplier {
	private String supplier_id;
	private String name;
	private String contact_info;
	private boolean isdeleted;
	private LocalDateTime created_at;

	public Supplier() {
	}

	public Supplier(String supplier_id, String name, String contact_info, boolean isdeleted, LocalDateTime created_at) {
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
