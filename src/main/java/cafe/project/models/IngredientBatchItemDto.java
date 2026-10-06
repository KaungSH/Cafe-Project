package cafe.project.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IngredientBatchItemDto {
	private String ingredientTypeId;
	private String ingredientTypeName;
	private String unit_name;
	private Double quantity_ordered;
	private BigDecimal unit_cost;
	private Double total_import_cost;
	private LocalDate manufactured_date;
	private LocalDate expire_date;
	private boolean selected; // Form selection checkbox 

	public  IngredientBatchItemDto() {}
	public  IngredientBatchItemDto( String ingredientTypeId,String ingredientTypeName,String unit_name,Double quantity_ordered,
			BigDecimal unit_cost,Double total_import_cost, LocalDate manufactured_date,LocalDate expire_date,boolean selected) {
		this.ingredientTypeId=ingredientTypeId;
		this.ingredientTypeName=ingredientTypeName;
		this.unit_name=unit_name;
		this.quantity_ordered=quantity_ordered;
		this.unit_cost=unit_cost;
		this.total_import_cost=total_import_cost;
		this.manufactured_date=manufactured_date;
		this.expire_date=expire_date;
		this.selected=selected;
	}
	public String getIngredientTypeId() {
		return ingredientTypeId;
	}
	public void setIngredientTypeId(String ingredientTypeId) {
		this.ingredientTypeId = ingredientTypeId;
	}
	public String getIngredientTypeName() {
		return ingredientTypeName;
	}
	public void setIngredientTypeName(String ingredientTypeName) {
		this.ingredientTypeName = ingredientTypeName;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public Double getQuantity_ordered() {
		return quantity_ordered;
	}
	public void setQuantity_ordered(Double quantity_ordered) {
		this.quantity_ordered = quantity_ordered;
	}
	public BigDecimal getUnit_cost() {
		return unit_cost;
	}
	public void setUnit_cost(BigDecimal unit_cost) {
		this.unit_cost = unit_cost;
	}
	public Double getTotal_import_cost() {
		return total_import_cost;
	}
	public void setTotal_import_cost(Double total_import_cost) {
		this.total_import_cost = total_import_cost;
	}
	public LocalDate getManufactured_date() {
		return manufactured_date;
	}
	public void setManufactured_date(LocalDate manufactured_date) {
		this.manufactured_date = manufactured_date;
	}
	public LocalDate getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(LocalDate expire_date) {
		this.expire_date = expire_date;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}