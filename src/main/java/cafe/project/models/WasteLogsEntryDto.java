package cafe.project.YatiWinLatt.models;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

	public class WasteLogsEntryDto {

	    private String waste_id;

	    @NotBlank(message = "Please select a batch ")
	    private String batch_id;

	    @NotBlank(message = "Please select waste reason")
	    private String waste_reason_id;

	    @NotNull(message = " Enter quantity lost ")
	    @DecimalMin(value = "0.001", message = "Quantity lost above 0")
	    private BigDecimal quantity_lost;

	    @NotNull(message = "Enter financial loss ")
	    @DecimalMin(value = "0.00", message = "Financial loss must 0 or above 0")
	    private BigDecimal financial_loss;

	    private String employee_id; 

	    @Size(max = 255, message = "Notes must not above 255")
	    private String notes;

	    public WasteLogsEntryDto() {}

		public String getWaste_id() {
			return waste_id;
		}

		public String getBatch_id() {
			return batch_id;
		}

		public String getWaste_reason_id() {
			return waste_reason_id;
		}

		public BigDecimal getQuantity_lost() {
			return quantity_lost;
		}

		public BigDecimal getFinancial_loss() {
			return financial_loss;
		}

		public String getEmployee_id() {
			return employee_id;
		}

		public String getNotes() {
			return notes;
		}

		public void setWaste_id(String waste_id) {
			this.waste_id = waste_id;
		}

		public void setBatch_id(String batch_id) {
			this.batch_id = batch_id;
		}

		public void setWaste_reason_id(String waste_reason_id) {
			this.waste_reason_id = waste_reason_id;
		}

		public void setQuantity_lost(BigDecimal quantity_lost) {
			this.quantity_lost = quantity_lost;
		}

		public void setFinancial_loss(BigDecimal financial_loss) {
			this.financial_loss = financial_loss;
		}

		public void setEmployee_id(String employee_id) {
			this.employee_id = employee_id;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

	    
}
