package cafe.project.models;

import java.math.BigDecimal;

public class DiscountCalculationDto {

	private BigDecimal price;
	private Integer quantity;
	private BigDecimal discountValue;
	private String promoTypeName;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}

	public String getPromoTypeName() {
		return promoTypeName;
	}

	public void setPromoTypeName(String promoTypeName) {
		this.promoTypeName = promoTypeName;
	}
}