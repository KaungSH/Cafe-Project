package cafe.project.models;

import java.util.List;

public class ProductQuantityRequiredModel {
	
	String product_id;
	List<String> ingredient_type_names;
	List<Double> qauntity_required;
	
	public ProductQuantityRequiredModel() {}
	
	public ProductQuantityRequiredModel(String product_id, List<String> ingredient_type_names, List<Double> qauntity_required) {
		this.product_id = product_id;
		this.ingredient_type_names = ingredient_type_names;
		this.qauntity_required = qauntity_required;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public List<String> getIngredient_type_names() {
		return ingredient_type_names;
	}

	public void setIngredient_type_names(List<String> ingredient_type_names) {
		this.ingredient_type_names = ingredient_type_names;
	}

	public List<Double> getQauntity_required() {
		return qauntity_required;
	}

	public void setQauntity_required(List<Double> qauntity_required) {
		this.qauntity_required = qauntity_required;
	}
	
	

}
