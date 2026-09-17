package cafe.project.KaungSattHein.models.fakes;

public class IngredientTypesListModelFake {
	
	private String ingredient_type_id, name, description, unit_abbreviation;
	
	public IngredientTypesListModelFake(String ingredient_type_id, String name, String description, String unit_abbreviation) {
		this.ingredient_type_id = ingredient_type_id;
		this.name = name;
		this.description = description;
		this.unit_abbreviation = unit_abbreviation;
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

	public String getUnit_abbreviation() {
		return unit_abbreviation;
	}

	public void setUnit_abbreviation(String unit_abbreviation) {
		this.unit_abbreviation = unit_abbreviation;
	}
	
	

}
