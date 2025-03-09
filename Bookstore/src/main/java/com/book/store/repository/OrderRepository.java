package com.book.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.book.store.dao.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	Page<Order> findAll(Pageable pageable);

}
