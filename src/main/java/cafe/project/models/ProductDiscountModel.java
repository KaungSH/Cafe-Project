package cafe.project.models;

import java.util.List;

public class ProductDiscountModel {
	
	private String product_id;
	private List<String> discount_ids, discount_names;
	private List<Double> discount_value;
	
	public ProductDiscountModel() {}
	
	public ProductDiscountModel(String product_id, List<String> discount_names, List<Double> discount_value, List<String> discount_ids) {
		this.product_id = product_id;
		this.discount_names = discount_names;
		this.discount_value = discount_value;
		this.discount_ids = discount_ids;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public List<String> getDiscount_names() {
		return discount_names;
	}

	public void setDiscount_names(List<String> discount_names) {
		this.discount_names = discount_names;
	}

	public List<Double> getDiscount_value() {
		return discount_value;
	}

	public void setDiscount_value(List<Double> discount_value) {
		this.discount_value = discount_value;
	}

	public List<String> getDiscount_ids() {
		return discount_ids;
	}

	public void setDiscount_ids(List<String> discount_ids) {
		this.discount_ids = discount_ids;
	}
	
	
	
	

}
