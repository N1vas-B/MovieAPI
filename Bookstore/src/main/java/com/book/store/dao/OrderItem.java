package com.book.store.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

//OrderItem Entity
@Data
@Entity
public class OrderItem {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "book_id")
	    private Book book;

	    private int quantity;
	    private double totalPrice;

	    @ManyToOne
	    @JoinColumn(name = "order_id")
	    @JsonBackReference
	    private Order order;

}
