package com.book.store.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.store.dto.BookDto;
import com.book.store.service.Bookservice;


@RestController
@RequestMapping("/api/books")
public class Bookcontroller
{
	
	@Autowired
	private Bookservice bookService;
	
	   
	   @PostMapping("/createbook") 
	   @PreAuthorize("hasRole('ADMIN')") 
	   public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) { // Get the currently authenticated user 
		   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		   String username = authentication.getName(); 
		   // Save book and get BookDTO back 
		   BookDto savedBookDTO = bookService.saveBook(bookDto, username); 
		   // Return the saved BookDTO in the response 
		   return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED); 
		   }
	   
	   
//	   @GetMapping("/allbooks")
//	   public ResponseEntity<List<BookDto>> getAllBooks()
//	   {
//		List<BookDto> books = bookService.getAllBooks();
//	  
//	   return new ResponseEntity<>(books, HttpStatus.OK);
//	   }
	   
	   
	   // Updated getAllBooks to support pagination
	    @GetMapping("/allbooks")
	    public ResponseEntity<Page<BookDto>> getAllBooks(
	        @RequestParam(defaultValue = "0") int page, 
	        @RequestParam(defaultValue = "10") int size) {

	        Page<BookDto> booksPage = bookService.getAllBooks(page, size);
	        return new ResponseEntity<>(booksPage, HttpStatus.OK);
	    }
	    
	    
	   @GetMapping("/getById/{id}")
	    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {
	        // Call the service method to get the book by ID
	        Optional<BookDto> bookDtoOptional = bookService.getBookById(id);

	        // If the book is found, return it with status OK (200)
	        if (bookDtoOptional.isPresent()) {
	            return new ResponseEntity<>(bookDtoOptional.get(), HttpStatus.OK);
	        }

	        // If the book is not found, return 404 Not Found
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	   
	   @PutMapping("/update/{id}")
	   public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long id,@RequestBody BookDto bookDto)
	   {
		   Optional<BookDto> existingBookDto = bookService.getBookById(id);
		   
		   if(existingBookDto.isPresent())
		   {
			   BookDto updatedBook = bookService.updateBook(id, bookDto);
			   return new ResponseEntity<>(updatedBook,HttpStatus.OK);
		   }
		   
		   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	   }
	   
	   @DeleteMapping("/delete/{id}")
	   public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id)
	   {
	        boolean isDeleted = bookService.deleteBookById(id);

	        if (isDeleted) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content - successful deletion
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 Not Found - book with ID does not exist
	        }
	    }
	   

//	    @GetMapping("/search/title/{title}")
//	    public ResponseEntity<List<BookDto>> getBooksByTitle(@PathVariable("title") String title)
//	    {
//	        List<BookDto> books = bookService.getBooksByTitle(title);
//	        return new ResponseEntity<>(books, HttpStatus.OK);
//	    }
	   
	   
	   // Updated getBooksByTitle to support pagination
	    @GetMapping("/search/title/{title}")
	    public ResponseEntity<Page<BookDto>> getBooksByTitle(
	        @PathVariable("title") String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	        Page<BookDto> booksPage = bookService.getBooksByTitle(title, page, size);
	        return new ResponseEntity<>(booksPage, HttpStatus.OK);
	    }

//	    @GetMapping("/search/author/{author}")
//	    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable("author") String author)
//	    {
//	       List<BookDto> books = bookService.getBooksByAuthor(author);
//	       return new ResponseEntity<>(books, HttpStatus.OK);
//	    }
	    
	    
	    // Updated getBooksByAuthor to support pagination
	    @GetMapping("/search/author/{author}")
	    public ResponseEntity<Page<BookDto>> getBooksByAuthor(
	        @PathVariable("author") String author,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	        Page<BookDto> booksPage = bookService.getBooksByAuthor(author, page, size);
	        return new ResponseEntity<>(booksPage, HttpStatus.OK);
	    }

}

	   

