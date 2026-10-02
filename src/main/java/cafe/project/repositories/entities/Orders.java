package cafe.project.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Orders {

	private String order_id;
	private String employee_id;
	private String branch_id;

	private LocalDateTime created_time;
	private LocalDateTime received_time;

	private boolean isedited;
	private boolean isdeleted;

	private BigDecimal total_amount;
	private int token_number;

	private String employee_name;
	private String branch_name;

	private boolean paymentDone;

	public Orders() {
	}

	public Orders(String order_id, String employee_id, String branch_id, LocalDateTime created_time,
			LocalDateTime received_time, boolean isedited, boolean isdeleted, BigDecimal total_amount, int token_number,
			String employee_name, String branch_name) {
		this.order_id = order_id;
		this.employee_id = employee_id;
		this.branch_id = branch_id;
		this.created_time = created_time;
		this.received_time = received_time;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.total_amount = total_amount;
		this.token_number = token_number;
		this.employee_name = employee_name;
		this.branch_name = branch_name;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public LocalDateTime getCreated_time() {
		return created_time;
	}

	public void setCreated_time(LocalDateTime created_time) {
		this.created_time = created_time;
	}

	public LocalDateTime getReceived_time() {
		return received_time;
	}

	public void setReceived_time(LocalDateTime received_time) {
		this.received_time = received_time;
	}

	public boolean isIsedited() {
		return isedited;
	}

	public void setIsedited(boolean isedited) {
		this.isedited = isedited;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public int getToken_number() {
		return token_number;
	}

	public void setToken_number(int token_number) {
		this.token_number = token_number;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public boolean isPaymentDone() {
		return paymentDone;
	}

	public void setPaymentDone(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}

}
