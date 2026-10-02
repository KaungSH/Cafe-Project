package cafe.project.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class IngredientBatchDto {

  @NotBlank(message = "Ingredient Batch ID is required")
  @Pattern(regexp = "^IB-.*$", message = "Ingredient Batch ID must start with IB-")
  private String batch_id;

  @NotNull(message = "Remaining quantity is required")
  @DecimalMin(value = "0.0", message = "Quantity cannot be negative")
  private BigDecimal remaining_quantity;

  @NotNull(message = "Manufactured date is required")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate manufactured_date;

  @NotNull(message = "Expire date is required")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate expire_date;

  @NotBlank(message = "Please select a branch")
  private String branch_name;

  private Boolean isdeleted;
  private LocalDateTime created_at;

  @NotBlank(message = "Please select ingredient type")
  private String ingredientType_name;

  @NotNull(message = "Unit cost is required")
  @DecimalMin(value = "0.0", message = "Unit cost cannot be negative")
  private BigDecimal unit_cost;
  
  @DecimalMin(value = "0.0", message = "Quantity ordered cannot be negative")
  private BigDecimal quantity_ordered;

  @DecimalMin(value = "0.0", message = "Total import cost cannot be negative")
  private BigDecimal total_import_cost;
  
  public IngredientBatchDto() {
  }

  public IngredientBatchDto(String batch_id, BigDecimal remaining_quantity, LocalDate manufactured_date,
      LocalDate expire_date, String branch_name, Boolean isdeleted, LocalDateTime created_at,
      String ingredientType_name, BigDecimal unit_cost,BigDecimal quantity_ordered,BigDecimal total_import_cost) {
    this.batch_id = batch_id;
    this.remaining_quantity = remaining_quantity;
    this.manufactured_date = manufactured_date;
    this.expire_date = expire_date;
    this.branch_name = branch_name;
    this.isdeleted = isdeleted;
    this.created_at = created_at;
    this.ingredientType_name = ingredientType_name;
    this.unit_cost = unit_cost;
    this.quantity_ordered=quantity_ordered;
    this.total_import_cost=total_import_cost;
 
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

  public String getBranch_name() {
	return branch_name;
  }

  public void setBranch_name(String branch_name) {
	this.branch_name = branch_name;
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

  public String getIngredientType_name() {
	return ingredientType_name;
  }

  public void setIngredientType_name(String ingredientType_name) {
	this.ingredientType_name = ingredientType_name;
  }

  public BigDecimal getUnit_cost() {
	return unit_cost;
  }

  public void setUnit_cost(BigDecimal unit_cost) {
	this.unit_cost = unit_cost;
  }

  public BigDecimal getQuantity_ordered() {
	return quantity_ordered;
  }

  public void setQuantity_ordered(BigDecimal quantity_ordered) {
	this.quantity_ordered = quantity_ordered;
  }

  public BigDecimal getTotal_import_cost() {
	return total_import_cost;
  }

  public void setTotal_import_cost(BigDecimal total_import_cost) {
	this.total_import_cost = total_import_cost;
  }
  

}