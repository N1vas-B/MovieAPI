package com.book.store.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
	
	 	private Long id;
	    private String customerName;
	    private String customerEmail;
	    private List<OrderItemDto> orderItems;
	    private String orderStatus;
	    private String paymentStatus;

}
