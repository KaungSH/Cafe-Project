package cafe.project.YatiWinLatt.models;

import java.math.BigDecimal;
import java.time.LocalDate;

	public class IngredientBatchDto {
	    private String batch_id;
	    private BigDecimal remaining_quantity;
	    private LocalDate manufactured_date;
	    private LocalDate expire_date;
	    private String branch_id;
	    private String import_detail_id;
	    private Boolean isexpired;
	    private Integer isdeleted;
	    private String ingredient_type_id;
	    private BigDecimal unit_cost;

	    public IngredientBatchDto() {}

	    public IngredientBatchDto(String batch_id, BigDecimal remaining_quantity, LocalDate manufactured_date, 
	                                LocalDate expire_date, String branch_id, String import_detail_id, BigDecimal unit_cost) {
	        this.batch_id = batch_id;
	        this.remaining_quantity = remaining_quantity;
	        this.manufactured_date = manufactured_date;
	        this.expire_date = expire_date;
	        this.branch_id = branch_id;
	        this.import_detail_id = import_detail_id;
	        this.unit_cost = unit_cost;
	        this.isexpired = false;
	        this.isdeleted = 0;
	    }

		public String getBatch_id() {
			return batch_id;
		}

		public BigDecimal getRemaining_quantity() {
			return remaining_quantity;
		}

		public LocalDate getManufactured_date() {
			return manufactured_date;
		}

		public LocalDate getExpire_date() {
			return expire_date;
		}

		public String getBranch_id() {
			return branch_id;
		}

		public String getImport_detail_id() {
			return import_detail_id;
		}

		public Boolean getIsexpired() {
			return isexpired;
		}

		public Integer getIsdeleted() {
			return isdeleted;
		}

		public String getIngredient_type_id() {
			return ingredient_type_id;
		}

		public BigDecimal getUnit_cost() {
			return unit_cost;
		}

		public void setBatch_id(String batch_id) {
			this.batch_id = batch_id;
		}

		public void setRemaining_quantity(BigDecimal remaining_quantity) {
			this.remaining_quantity = remaining_quantity;
		}

		public void setManufactured_date(LocalDate manufactured_date) {
			this.manufactured_date = manufactured_date;
		}

		public void setExpire_date(LocalDate expire_date) {
			this.expire_date = expire_date;
		}

		public void setBranch_id(String branch_id) {
			this.branch_id = branch_id;
		}

		public void setImport_detail_id(String import_detail_id) {
			this.import_detail_id = import_detail_id;
		}

		public void setIsexpired(Boolean isexpired) {
			this.isexpired = isexpired;
		}

		public void setIsdeleted(Integer isdeleted) {
			this.isdeleted = isdeleted;
		}

		public void setIngredient_type_id(String ingredient_type_id) {
			this.ingredient_type_id = ingredient_type_id;
		}

		public void setUnit_cost(BigDecimal unit_cost) {
			this.unit_cost = unit_cost;
		}

		
	   
}
