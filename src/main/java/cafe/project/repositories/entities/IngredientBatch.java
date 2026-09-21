package cafe.project.NayZarLinn.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class IngredientBatch {

	private String batchId;
	private BigDecimal remainingQuantity;
	private LocalDate manufacturedDate;
	private LocalDate expireDate;
	private String branchId;
	private String importDetailId;
	private Boolean isExpired;
	private Boolean isDeleted;
	private LocalDateTime createdAt;
	private String ingredientTypeId;
	private BigDecimal unitCost;
	private String branchName;
	private String ingredientTypeName;

	public IngredientBatch() {
	}

	public IngredientBatch(String batchId, BigDecimal remainingQuantity, LocalDate manufacturedDate,
			LocalDate expireDate, String branchId, String importDetailId, Boolean isExpired, Boolean isDeleted,
			LocalDateTime createdAt, String ingredientTypeId, BigDecimal unitCost,String branchName,
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
		this.ingredientTypeName = ingredientTypeName;
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

	public void setRemainingQuantity(BigDecimal remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
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

	public void setRmainingQuantity(BigDecimal remainingQuantity) {
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

}
