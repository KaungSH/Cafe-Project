package cafe.project.models;

import java.util.List;

import cafe.project.repositories.entities.OrderDetails;

public class OrderDetailsDto {

	private List<OrderDetails> orderDetails;

	public OrderDetailsDto() {
	}

	public OrderDetailsDto(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

}