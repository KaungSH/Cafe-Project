package cafe.project.HeinMinHtet.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdersDto {

	private String order_id;
	private String employee_id;
	private String customer_id;
	private String branch_id;
	private LocalDateTime created_time;
	private LocalDateTime received_time;
	private boolean isedited;
	private boolean isdeleted;
	private String order_type_id;
	private BigDecimal total_amount;
	private int tokenNumber;
	private String customerAddress_id;
	
	private OrderStatus orderStatus;
	
	public OrdersDto() {}
	
	public OrdersDto(String order_id,String employee_id,String customer_id,
			String branch_id,LocalDateTime created_time,LocalDateTime received_time,
			boolean isedited,boolean isdeleted,String order_type_id,BigDecimal total_amount,
			int tokenNumber,String customerAddress_id,OrderStatus orderStatus) {
		this.order_id=order_id;
		this.employee_id=employee_id;
		this.customer_id=customer_id;
		this.branch_id=branch_id;
		this.created_time=created_time;
		this.received_time=received_time;
		this.isedited=isedited;
		this.isdeleted=isdeleted;
		this.order_type_id=order_type_id;
		this.total_amount=total_amount;
		this.tokenNumber=tokenNumber;
		this.customerAddress_id=customerAddress_id;
		this.orderStatus=orderStatus;
	}

	public String getOrder_id() {
		return order_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public LocalDateTime getCreated_time() {
		return created_time;
	}

	public LocalDateTime getReceived_time() {
		return received_time;
	}

	public boolean isIsedited() {
		return isedited;
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public String getOrder_type_id() {
		return order_type_id;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public int getTokenNumber() {
		return tokenNumber;
	}

	public String getCustomerAddress_id() {
		return customerAddress_id;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public void setCreated_time(LocalDateTime created_time) {
		this.created_time = created_time;
	}

	public void setReceived_time(LocalDateTime received_time) {
		this.received_time = received_time;
	}

	public void setIsedited(boolean isedited) {
		this.isedited = isedited;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public void setOrder_type_id(String order_type_id) {
		this.order_type_id = order_type_id;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public void setTokenNumber(int tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public void setCustomerAddress_id(String customerAddress_id) {
		this.customerAddress_id = customerAddress_id;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	

}
