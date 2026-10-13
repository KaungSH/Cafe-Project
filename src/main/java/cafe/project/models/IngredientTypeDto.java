package cafe.project.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class IngredientTypeDto {
	@NotBlank(message = "Ingredient Type ID is required")
	@Pattern(regexp = "^IT-.*$", message = "Ingredient Type ID must start with IT-")
	private String ingredient_type_id;

	@NotBlank(message = "Name is required")
	//@Size(max = 200, message = "Name must be 200 characters or fewer")
	private String name;

	private String description;

	@NotBlank(message = "Unit is required")
	private String unit_id;
	
	private boolean isdeleted;
	private String Uname; //unit name
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
	public String getUname() {
		return Uname;
	}
	public void setUname(String uname) {
		Uname = uname;
	}
	

	
	
	
}