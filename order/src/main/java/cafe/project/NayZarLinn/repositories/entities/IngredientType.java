package cafe.project.NayZarLinn.repositories.entities;

import java.time.LocalDateTime;

public class IngredientType {
	private String ingredient_type_id;
	private String name;
	private String description;
	private String unit_id;
	private boolean isdeleted;
	private LocalDateTime created_at;

	public IngredientType() {
	}

	public IngredientType(String ingredient_type_id, String name, String description, String unit_id, boolean isdeleted,
			LocalDateTime created_at) {
		this.ingredient_type_id = ingredient_type_id;
		this.name = name;
		this.description = description;
		this.unit_id = unit_id;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
	}

	public String getIngredient_type_id() {
		return ingredient_type_id;
	}

	public void setIngredient_type_id(String ingredient_type_id) {
		this.ingredient_type_id = ingredient_type_id;
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

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
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
