package cafe.project.models;

public class CheckoutForm {
	private String orderDetailCode;
    private String customerName;
    private String remark; // Remark အတွက် Field

    // Getters and Setters
    public String getOrderDetailCode() { return orderDetailCode; }
    public void setOrderDetailCode(String orderDetailCode) { this.orderDetailCode = orderDetailCode; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}

