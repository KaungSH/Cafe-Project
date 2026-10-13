package cafe.project.repositories.entities;

import java.math.BigDecimal;

public class OrderDetails {

	private String order_detail_id;
	private String product_id;
	private String order_id;
	private Integer quantity;
	private String remark;
	private String product_name;
	private BigDecimal price;
	
	public OrderDetails() {
	}

	public OrderDetails(String order_detail_id, String product_id, String order_id, Integer quantity, String remark,
			String product_name,BigDecimal price) {
		this.order_detail_id = order_detail_id;
		this.product_id = product_id;
		this.order_id = order_id;
		this.quantity = quantity;
		this.remark = remark;
		this.product_name = product_name;
	}

	public String getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(String order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public BigDecimal getPrice() {
	    return price;
	}

	public void setPrice(BigDecimal price) {
	    this.price = price;
	}

}
