package cafe.project.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class IngredientBatch {

	private String batch_id;
	private BigDecimal remaining_quantity;
	private LocalDate manufactured_date;
	private LocalDate expire_date;
	private String branch_id;
	private Boolean isdeleted;
	private LocalDateTime created_at;
	private String ingredient_type_id;
	private BigDecimal unit_cost;
	private double quantity_ordered;
	private double total_import_cost;
	private String import_id;
	
	private String branch_name; //branch name
	private String ingredientType_name; //ingredientTypeName
	private String unit_name;
	
	
	public IngredientBatch() {
	}

	public IngredientBatch(String batch_id, BigDecimal remaining_quantity, LocalDate manufactured_date,
			LocalDate expire_date, String branch_id, Boolean isdeleted,
			LocalDateTime created_at, String ingredient_type_id, BigDecimal unit_cost,double quantity_ordered,
			double total_import_cost,String import_id, String branch_name,String ingredientType_name,String unit_name) {
		this.batch_id = batch_id;
		this.remaining_quantity = remaining_quantity;
		this.manufactured_date = manufactured_date;
		this.expire_date = expire_date;
		this.branch_id = branch_id;
		this.isdeleted = isdeleted;
		this.created_at = created_at;
		this.ingredient_type_id = ingredient_type_id;
		this.unit_cost = unit_cost;
		this.quantity_ordered=quantity_ordered;
		this.total_import_cost=total_import_cost;
		this.import_id=import_id;
		this.branch_name=branch_name;
		this.ingredientType_name = ingredientType_name;
		this.unit_name=unit_name;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public BigDecimal getRemaining_quantity() {
		return remaining_quantity;
	}

	public void setRemaining_quantity(BigDecimal remaining_quantity) {
		this.remaining_quantity = remaining_quantity;
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

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}


	public Boolean getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public String getIngredient_type_id() {
		return ingredient_type_id;
	}

	public void setIngredient_type_id(String ingredient_type_id) {
		this.ingredient_type_id = ingredient_type_id;
	}

	public BigDecimal getUnit_cost() {
		return unit_cost;
	}

	public void setUnit_cost(BigDecimal unit_cost) {
		this.unit_cost = unit_cost;
	}

	public double getQuantity_ordered() {
		return quantity_ordered;
	}

	public void setQuantity_ordered(double quantity_ordered) {
		this.quantity_ordered = quantity_ordered;
	}

	public double getTotal_import_cost() {
		return total_import_cost;
	}

	public void setTotal_import_cost(double total_import_cost) {
		this.total_import_cost = total_import_cost;
	}

	public String getImport_id() {
		return import_id;
	}

	public void setImport_id(String import_id) {
		this.import_id = import_id;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getIngredientType_name() {
		return ingredientType_name;
	}

	public void setIngredientType_name(String ingredientType_name) {
		this.ingredientType_name = ingredientType_name;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	
}