package com.book.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.store.dao.Order;
import com.book.store.dao.User;
import com.book.store.dto.OrderDto;
import com.book.store.service.OrderService;
import com.book.store.service.Userservice;

//Order Controller
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	 @Autowired
	 private OrderService orderService;
	 
	 @Autowired 
	 private Userservice userService;

	    @PostMapping("/place")
	    public ResponseEntity<Order> placeOrder(@RequestBody OrderDto orderDto) {
	    	User user = userService.findByName(orderDto.getCustomerName());
	    	if(user.getRoles().equalsIgnoreCase("USER") ) {
	        Order placedOrder = orderService.placeOrder(orderDto, user);
	        return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
	    	}
	    	else {
	    		return null;
	    	}

	    }

//	    @GetMapping
//	    @PreAuthorize("hasRole('ADMIN')")
//	    public ResponseEntity<List<OrderDto>> getAllOrders() {
//	        return ResponseEntity.ok(orderService.getAllOrders());
//	    }
	    
	    
	 // Updated getAllOrders to support pagination
	    @GetMapping
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Page<OrderDto>> getAllOrders(
	            @RequestParam(defaultValue = "0") int page,  // Default page number is 0
	            @RequestParam(defaultValue = "10") int size) {  // Default page size is 10

	        Page<OrderDto> ordersPage = orderService.getAllOrders(page, size);
	        return ResponseEntity.ok(ordersPage);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
	        return ResponseEntity.ok(orderService.getOrder(id));
	    }

	    @PutMapping("/{id}/status")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
	        orderService.updateOrderStatus(id, status);
	        return ResponseEntity.ok().build();
	    }

}
