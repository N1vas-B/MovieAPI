package com.book.store.dto;

import lombok.Data;

@Data
public class OrderItemDto {
	
    private Long bookId;
    private int quantity;
    private double totalPrice;

}
