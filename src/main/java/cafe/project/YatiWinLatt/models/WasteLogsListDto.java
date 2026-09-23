package cafe.project.YatiWinLatt.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

	public class WasteLogsListDto {
	    private String waste_id;
	    private String batch_id;
	    private String reason_name;
	    private BigDecimal quantity_lost;
	    private BigDecimal financial_loss;
	    private String name; //employee-name
	    private LocalDateTime logged_at;
	    private String notes;

	    public WasteLogsListDto() {}

		public String getWaste_id() {
			return waste_id;
		}

		public String getBatch_id() {
			return batch_id;
		}

		public String getReason_name() {
			return reason_name;
		}

		public BigDecimal getQuantity_lost() {
			return quantity_lost;
		}

		public BigDecimal getFinancial_loss() {
			return financial_loss;
		}

		public String getName() {
			return name;
		}

		public LocalDateTime getLogged_at() {
			return logged_at;
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

		public void setReason_name(String reason_name) {
			this.reason_name = reason_name;
		}

		public void setQuantity_lost(BigDecimal quantity_lost) {
			this.quantity_lost = quantity_lost;
		}

		public void setFinancial_loss(BigDecimal financial_loss) {
			this.financial_loss = financial_loss;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setLogged_at(LocalDateTime logged_at) {
			this.logged_at = logged_at;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}
	    
	    
}
