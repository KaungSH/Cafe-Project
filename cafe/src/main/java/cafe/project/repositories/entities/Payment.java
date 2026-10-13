package cafe.project.repositories.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {

	private String payment_id;
	private String order_id;
	private LocalDateTime paid_time;
	private String method_id;
	private String note;
	private boolean isedited;
	private boolean isdeleted;
	private LocalDate date;
	private String filepath;
	private String employee_id;
	private Integer token_number;
	private String pay_method_name;
	private String employee_name;
	private String branch_name;
	private BigDecimal total_amount;
	private String product_name;
	private Double discount;

	public Payment() {
	}

	public Payment(String payment_id, String order_id, LocalDateTime paid_time, String method_id, String note,
			boolean isedited, boolean isdeleted, LocalDate date, String filepath, String employee_id) {

		this.payment_id = payment_id;
		this.order_id = order_id;
		this.paid_time = paid_time;
		this.method_id = method_id;
		this.note = note;
		this.isedited = isedited;
		this.isdeleted = isdeleted;
		this.date = date;
		this.filepath = filepath;
		this.employee_id = employee_id;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public LocalDateTime getPaid_time() {
		return paid_time;
	}

	public void setPaid_time(LocalDateTime paid_time) {
		this.paid_time = paid_time;
	}

	public String getMethod_id() {
		return method_id;
	}

	public void setMethod_id(String method_id) {
		this.method_id = method_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public Integer getToken_number() {
		return token_number;
	}

	public void setToken_number(Integer token_number) {
		this.token_number = token_number;
	}

	public String getPay_method_name() {
		return pay_method_name;
	}

	public void setPay_method_name(String pay_method_name) {
		this.pay_method_name = pay_method_name;
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

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Double getDiscount() {
	    return discount;
	}
	public void setDiscount(Double discount) {
	    this.discount = discount;
	}
}