package cafe.project.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class DailyRegisterListDto {
  private String register_id;
  private String branch_name;
  private String employee_name;
  private String status_name;
  private LocalDate date;
  private LocalTime opened_at;
  private LocalTime closed_at;

  public DailyRegisterListDto() {}

  public DailyRegisterListDto(String register_id, String branch_name, String employee_name, 
                              String status_name, LocalDate date, LocalTime opened_at, LocalTime closed_at) {
    this.register_id = register_id;
    this.branch_name = branch_name;
    this.employee_name = employee_name;
    this.status_name = status_name;
    this.date = date;
    this.opened_at = opened_at;
    this.closed_at = closed_at;
  }

  public String getRegister_id() {
	return register_id;
  }

  public String getBranch_name() {
	return branch_name;
  }

  public String getEmployee_name() {
	return employee_name;
  }

  public String getStatus_name() {
	return status_name;
  }

  public LocalDate getDate() {
	return date;
  }

  public LocalTime getOpened_at() {
	return opened_at;
  }

  public LocalTime getClosed_at() {
	return closed_at;
  }

  public void setRegister_id(String register_id) {
	this.register_id = register_id;
  }

  public void setBranch_name(String branch_name) {
	this.branch_name = branch_name;
  }

  public void setEmployee_name(String employee_name) {
	this.employee_name = employee_name;
  }

  public void setStatus_name(String status_name) {
	this.status_name = status_name;
  }

  public void setDate(LocalDate date) {
	this.date = date;
  }

  public void setOpened_at(LocalTime opened_at) {
	this.opened_at = opened_at;
  }

  public void setClosed_at(LocalTime closed_at) {
	this.closed_at = closed_at;
  }

}