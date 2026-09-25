package cafe.project.repositories.entities;

import java.time.LocalDateTime;

public class IngredientType {
	private String ingredientTypeId;
	private String name;
	private String description;
	private String unitId;
	private boolean isDeleted;
	private String unitAbbreviation;
	private LocalDateTime createdAt;

	public IngredientType() {
	}

	public IngredientType(String ingredientTypeId, String name, String description, String unitId, boolean isDeleted,
			String unitAbbreviation) {
		this.ingredientTypeId = ingredientTypeId;
		this.name = name;
		this.description = description;
		this.unitId = unitId;
		this.isDeleted = isDeleted;
		this.unitAbbreviation = unitAbbreviation;
	}

	public String getIngredientTypeId() {
		return ingredientTypeId;
	}

	public void setIngredientTypeId(String ingredientTypeId) {
		this.ingredientTypeId = ingredientTypeId;
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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getUnitAbbreviation() {
		return unitAbbreviation;
	}

	public void setUnitAbbreviation(String unitAbbreviation) {
		this.unitAbbreviation = unitAbbreviation;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}