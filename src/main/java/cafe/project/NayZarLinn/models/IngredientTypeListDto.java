package cafe.project.NayZarLinn.models;

import java.time.LocalDateTime;

public class IngredientTypeListDto {
	private String ingredient_type_id;
	private String name;
	private String description;
	private String abbreviation;
	private boolean isdeleted;
	private LocalDateTime created_at;

	public IngredientTypeListDto() {
	}

	public IngredientTypeListDto(String ingredient_type_id, String name, String description, String abbreviation,
			boolean isdeleted, LocalDateTime created_at) {
		this.ingredient_type_id = ingredient_type_id;
		this.name = name;
		this.description = description;
		this.abbreviation = abbreviation;
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

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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
