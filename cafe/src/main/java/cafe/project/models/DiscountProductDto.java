package cafe.project.models;

import java.util.List;

public class DiscountProductDto {
	private String discount_id;
	private String product_id;
	private String discount_name;
	private String product_name;

	private List<String> product_ids;
	
	public DiscountProductDto() {
	}

	public String getDiscount_id() {
		return discount_id;
	}

	public void setDiscount_id(String discount_id) {
		this.discount_id = discount_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getDiscount_name() {
		return discount_name;
	}

	public void setDiscount_name(String discount_name) {
		this.discount_name = discount_name;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public List<String> getProduct_ids() {
		return product_ids;
	}

	public void setProduct_ids(List<String> product_ids) {
		this.product_ids = product_ids;
	}

}
