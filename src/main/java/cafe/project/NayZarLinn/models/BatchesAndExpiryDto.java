package cafe.project.NayZarLinn.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BatchesAndExpiryDto {

	private String batchId;
	private String ingredientTypeName;
	private LocalDate manufacturedDate;
	private LocalDate expireDate;
	private BigDecimal remainingQty;
	private BigDecimal unitCost;
	private long daysLeft;
	private String status;
	private String importDetailId;
	private BigDecimal quantityOrdered;
	private BigDecimal lineTotal;

	public BatchesAndExpiryDto() {
	}

	public BatchesAndExpiryDto(String batchId, String ingredientTypeName, LocalDate manufacturedDate,
			LocalDate expireDate, BigDecimal remainingQty, BigDecimal unitCost, String importDetailId,
			BigDecimal quantityOrdered, BigDecimal lineTotal) {
		this.batchId = batchId;
		this.ingredientTypeName = ingredientTypeName;
		this.manufacturedDate = manufacturedDate;
		this.expireDate = expireDate;
		this.remainingQty = remainingQty;
		this.unitCost = unitCost;
		this.importDetailId = importDetailId;
		this.quantityOrdered = quantityOrdered;
		this.lineTotal = lineTotal;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getIngredientTypeName() {
		return ingredientTypeName;
	}

	public void setIngredientTypeName(String ingredientTypeName) {
		this.ingredientTypeName = ingredientTypeName;
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

	public BigDecimal getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(BigDecimal remainingQty) {
		this.remainingQty = remainingQty;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public long getDaysLeft() {
		return daysLeft;
	}

	public void setDaysLeft(long daysLeft) {
		this.daysLeft = daysLeft;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImportDetailId() {
		return importDetailId;
	}

	public void setImportDetailId(String importDetailId) {
		this.importDetailId = importDetailId;
	}

	public BigDecimal getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(BigDecimal quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public BigDecimal getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}

}
