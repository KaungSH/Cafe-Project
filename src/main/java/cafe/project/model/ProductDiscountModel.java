package cafe.project.model;

import java.util.List;

public class ProductDiscountModel {
	
	String product_id;
	List<String> discount_ids;
	List<Double> discount_value;
	
	public ProductDiscountModel() {}
	
	public ProductDiscountModel(String product_id, List<String> discount_ids, List<Double> discount_value) {
		this.product_id = product_id;
		this.discount_ids = discount_ids;
		this.discount_value = discount_value;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public List<String> getDiscount_ids() {
		return discount_ids;
	}

	public void setDiscount_ids(List<String> discount_ids) {
		this.discount_ids = discount_ids;
	}

	public List<Double> getDiscount_value() {
		return discount_value;
	}

	public void setDiscount_value(List<Double> discount_value) {
		this.discount_value = discount_value;
	}
	
	

}
