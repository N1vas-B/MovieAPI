package com.book.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.store.dao.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Bookrepo extends JpaRepository<Book,Long>
{
	//List<Book> findByTitle(String title);
	
	// Return a Page instead of List for pagination
    Page<Book> findByTitle(String title, Pageable pageable);

    Page<Book> findByAuthor(String author, Pageable pageable);
	
   // List<Book> findByAuthor(String author);
    
	
}
