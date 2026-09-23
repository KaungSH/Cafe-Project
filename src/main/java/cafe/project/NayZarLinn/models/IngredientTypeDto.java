package cafe.project.NayZarLinn.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class IngredientTypeDto {
	@NotBlank(message = "Ingredient Type ID is required")
	@Pattern(regexp = "^IT-.*$", message = "Ingredient Type ID must start with IT-")
	private String ingredientTypeId;

	@NotBlank(message = "Name is required")
	@Size(max = 200, message = "Name must be 200 characters or fewer")
	private String name;

	private String description;

	@NotBlank(message = "Unit is required")
	private String unitId;

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

}
