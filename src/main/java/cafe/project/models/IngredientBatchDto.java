package cafe.project.NayZarLinn.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IngredientBatchDto {

	@NotBlank(message = "Batch ID is requird")
	private String batchId;

	@NotNull(message = "Remaining quantity is required")
	@DecimalMin(value = "0.0", message = "Quantity cannot be negative")
	private BigDecimal remainingQuantity;

	@NotNull(message = "Manufactured date is required")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate manufacturedDate;

	@NotNull(message = "Expire date is required")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate expireDate;

	@NotBlank(message = "Please select a branch Id")
	private String branchId;

	@NotBlank(message = "Please select import detail")
	private String importDetailId;

	private Boolean isExpired;
	private Boolean isDeleted;
	private LocalDateTime createdAt;

	@NotBlank(message = "Please select ingredient type")
	private String ingredientTypeId;

	@NotNull(message = "Unit cost is required")
	@DecimalMin(value = "0.0", message = "Unit cost cannot be negative")
	private BigDecimal unitCost;
	
	private String branchName;
	
	private String ingredientTypeName;

	public IngredientBatchDto() {
	}

	public IngredientBatchDto(String batchId, BigDecimal remainingQuantity, LocalDate manufacturedDate,
			LocalDate expireDate, String branchId, String importDetailId, Boolean isExpired, Boolean isDeleted,
			LocalDateTime createdAt, String ingredientTypeId, BigDecimal unitCost, String branchName,
			String ingredientTypeName) {
		this.batchId = batchId;
		this.remainingQuantity = remainingQuantity;
		this.manufacturedDate = manufacturedDate;
		this.expireDate = expireDate;
		this.branchId = branchId;
		this.importDetailId = importDetailId;
		this.isExpired = isExpired;
		this.isDeleted = isDeleted;
		this.createdAt = createdAt;
		this.ingredientTypeId = ingredientTypeId;
		this.unitCost = unitCost;
		this.branchName = branchName;
		
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public BigDecimal getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(BigDecimal remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}

	public LocalDate getManufacturedDate() {
		return manufacturedDate;
	}

	public void setManufacturedDate(LocalDate manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getImportDetailId() {
		return importDetailId;
	}

	public void setImportDetailId(String importDetailId) {
		this.importDetailId = importDetailId;
	}

	public Boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getIngredientTypeId() {
		return ingredientTypeId;
	}

	public void setIngredientTypeId(String ingredientTypeId) {
		this.ingredientTypeId = ingredientTypeId;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIngredientTypeName() {
		return ingredientTypeName;
	}

	public void setIngredientTypeName(String ingredientTypeName) {
		this.ingredientTypeName = ingredientTypeName;
	}

}
