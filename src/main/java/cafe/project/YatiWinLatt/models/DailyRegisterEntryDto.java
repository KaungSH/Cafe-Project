package cafe.project.YatiWinLatt.models;

import java.sql.Time;
import java.time.LocalDate;


public class DailyRegisterEntryDto {
	private String register_id;
	private String branch_id;
	private String employee_id;
	private String register_status_id;
	private LocalDate date;
	private Time opened_at;
	private Time closed_at;
	
	public String getRegister_id() {
		return register_id;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public String getRegister_status_id() {
		return register_status_id;
	}
	public LocalDate getDate() {
		return date;
	}
	public Time getOpened_at() {
		return opened_at;
	}
	public Time getClosed_at() {
		return closed_at;
	}
	public void setRegister_id(String register_id) {
		this.register_id = register_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public void setRegister_status_id(String register_status_id) {
		this.register_status_id = register_status_id;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setOpened_at(Time opened_at) {
		this.opened_at = opened_at;
	}
	public void setClosed_at(Time closed_at) {
		this.closed_at = closed_at;
	}

	
}