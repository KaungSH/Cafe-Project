package cafe.project.YatiWinLatt.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WasteLogs {

	private String waste_id; 
	private String batch_id ;
	private String waste_reason_id ; 
	private BigDecimal quantity_lost; 
	private BigDecimal  financial_loss ;
	private String  employee_id ;
	private LocalDateTime logged_at ;
	private String notes ; 
	private boolean isdeleted;
	
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
	public LocalDateTime getLogged_at() {
		return logged_at;
	}
	public String getNotes() {
		return notes;
	}
	public boolean isIsdeleted() {
		return isdeleted;
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
	public void setLogged_at(LocalDateTime logged_at) {
		this.logged_at = logged_at;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	
	
}
