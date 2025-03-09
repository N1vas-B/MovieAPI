package com.book.store.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.book.store.dao.Book;
import com.book.store.dao.Order;
import com.book.store.dao.OrderItem;
import com.book.store.dao.User;
import com.book.store.dto.OrderDto;
import com.book.store.dto.OrderItemDto;
import com.book.store.repository.Bookrepo;
import com.book.store.repository.OrderRepository;
import com.book.store.repository.UserRepository;

@Service
public class OrderService {
	
	
	    @Autowired
	    private OrderRepository orderRepository;

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private Bookrepo bookRepository;

	    public Order placeOrder(OrderDto orderDto, User user) {
	      

	        Order order = new Order();
	        order.setUser(user);
	        order.setOrderStatus("Pending");
	        order.setPaymentStatus("paid");

	        final Order finalOrder = order;  // Assign to effectively final variable
	        List<OrderItem> orderItems = orderDto.getOrderItems().stream().map(itemDto -> {
	            Book book = bookRepository.findById(itemDto.getBookId())
	                .orElseThrow(() -> new RuntimeException("Book not found"));
	            OrderItem orderItem = new OrderItem();
	            orderItem.setBook(book);
	            orderItem.setQuantity(itemDto.getQuantity());
	            orderItem.setTotalPrice(book.getPrice() * itemDto.getQuantity());
	            orderItem.setOrder(finalOrder);
	            return orderItem;
	        }).collect(Collectors.toList());

	        order.setOrderItems(orderItems);
	        order = orderRepository.save(order);
	        orderDto.setId(order.getId());
	        return order;
	    }

	    public OrderDto getOrder(Long id) {
	        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
	        return mapToDto(order);
	    }

//	    public List<OrderDto> getAllOrders() {
//	        return orderRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
//	    }

	    

	    // Updated getAllOrders to support pagination
	    public Page<OrderDto> getAllOrders(int page, int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Order> ordersPage = orderRepository.findAll(pageable);

	        // Convert Page<Order> to Page<OrderDto>
	        return ordersPage.map(this::mapToDto);
	    }
	    
	    public void updateOrderStatus(Long id, String status) {
	        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
	        order.setOrderStatus(status);
	        orderRepository.save(order);
	    }

	    private OrderDto mapToDto(Order order) {
	        OrderDto dto = new OrderDto();
	        dto.setId(order.getId());
	        dto.setCustomerName(order.getUser().getName());
	        dto.setCustomerEmail(order.getUser().getEmail());
	        dto.setOrderStatus(order.getOrderStatus());
	        dto.setPaymentStatus(order.getPaymentStatus());
	        dto.setOrderItems(order.getOrderItems().stream().map(item -> {
	            OrderItemDto itemDto = new OrderItemDto();
	            itemDto.setBookId(item.getBook().getId());
	            itemDto.setQuantity(item.getQuantity());
	            itemDto.setTotalPrice(item.getTotalPrice());
	            return itemDto;
	        }).collect(Collectors.toList()));
	        return dto;
	    }
}
