package com.book.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.store.dao.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
