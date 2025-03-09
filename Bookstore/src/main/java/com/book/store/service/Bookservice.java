package com.book.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.book.store.dao.Book;
import com.book.store.dao.User;
import com.book.store.dto.BookDto;
import com.book.store.repository.Bookrepo;
import com.book.store.repository.UserRepository;

@Service
public class Bookservice {
	@Autowired
	private Bookrepo bookrepo;
	
	@Autowired 
	private UserRepository userRepository;
	
	
	public BookDto saveBook(BookDto bookDto, String username) 
	{// Find the user by username 
		
		User user = userRepository.findByName(username) .orElseThrow(() -> new RuntimeException("User not found")); 
		// Map BookDto to Book
		Book book = new Book();
		book.setTitle(bookDto.getTitle()); 
		book.setAuthor(bookDto.getAuthor()); 
		book.setGenre(bookDto.getGenre()); 
		book.setIsbn(bookDto.getIsbn());
		book.setPrice(bookDto.getPrice()); 
		book.setDescription(bookDto.getDescription()); 
		book.setStockQuantity(bookDto.getStockQuantity()); 
		book.setImageUrl(bookDto.getImageUrl());
		book.setUser(user); // Set the user who created the book
		book.setCreatedBy(user.getName()); // Set the createdBy field with the user's name 
		// Save the book
		book = bookrepo.save(book); // Convert to BookDto 
		bookDto.setId(book.getId());
		return bookDto; 
		
	}

	

	public BookDto saveBook(BookDto bookDto)
	{
		Book book = new Book();
		book.setAuthor(bookDto.getAuthor());
		book.setDescription(bookDto.getDescription());
		book.setGenre(bookDto.getGenre());
		book.setImageUrl(bookDto.getImageUrl());
		book.setIsbn(bookDto.getIsbn());
		book.setPrice(bookDto.getPrice());
		book.setTitle(bookDto.getTitle());
		book.setStockQuantity(bookDto.getStockQuantity());
		Book savedBook = bookrepo.save(book);

		// Convert saved Book back to BookDTO
		BookDto savedBookDTO = new BookDto();
		// savedBookDTO.setId(savedBook.getId());
		savedBookDTO.setTitle(savedBook.getTitle());
		savedBookDTO.setAuthor(savedBook.getAuthor());
		savedBookDTO.setGenre(savedBook.getGenre());
		savedBookDTO.setIsbn(savedBook.getIsbn());
		savedBookDTO.setPrice(savedBook.getPrice());
		savedBookDTO.setDescription(savedBook.getDescription());
		savedBookDTO.setStockQuantity(savedBook.getStockQuantity());
		savedBookDTO.setImageUrl(savedBook.getImageUrl());
		return bookDto;
	}

  // Updated getAllBooks to support pagination
    public Page<BookDto> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookrepo.findAll(pageable);

        // Convert Page<Book> to Page<BookDto>
        return booksPage.map(this::convertToBookDto);
    }
	
	public Optional<BookDto> getBookById(Long id)
	{
        Optional<Book> bookOptional = bookrepo.findById(id);
        
        if(bookOptional.isPresent())
        {
        	Book book = bookOptional.get();
        			
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setTitle(book.getTitle());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setGenre(book.getGenre());
            bookDto.setIsbn(book.getIsbn());
            bookDto.setPrice(book.getPrice());
            bookDto.setDescription(book.getDescription());
            bookDto.setStockQuantity(book.getStockQuantity());
            bookDto.setImageUrl(book.getImageUrl());
            
            return Optional.of(bookDto);
        }
        return Optional.empty();
	}
	
	public BookDto updateBook(Long id, BookDto bookDto) {
        // Find the existing book by ID
        Optional<Book> existingBookOptional = bookrepo.findById(id);

        if (existingBookOptional.isPresent()) {
            // Get the existing book from the database
            Book existingBook = existingBookOptional.get();

            // Update the fields of the book
            if (bookDto.getPrice() != null) {
                existingBook.setPrice(bookDto.getPrice());
            }
            if (bookDto.getStockQuantity() != null) {
                existingBook.setStockQuantity(bookDto.getStockQuantity());
            }
            if (bookDto.getTitle() != null) {
                existingBook.setTitle(bookDto.getTitle());
            }
            if (bookDto.getAuthor() != null) {
                existingBook.setAuthor(bookDto.getAuthor());
            }
            if (bookDto.getGenre() != null) {
                existingBook.setGenre(bookDto.getGenre());
            }
            if (bookDto.getIsbn() != null) {
                existingBook.setIsbn(bookDto.getIsbn());
            }
            if (bookDto.getDescription() != null) {
                existingBook.setDescription(bookDto.getDescription());
            }
            if (bookDto.getImageUrl() != null) {
                existingBook.setImageUrl(bookDto.getImageUrl());
            }

            // Save the updated book to the database
            Book updatedBook = bookrepo.save(existingBook);

            // Convert the updated book to BookDto and return
            return convertToBookDto(updatedBook);
        }

        // If book not found, return null (could also throw exception if needed)
        return null;
    }

    // Utility method to convert Book entity to BookDto
    private BookDto convertToBookDto(Book book)
    {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setGenre(book.getGenre());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setStockQuantity(book.getStockQuantity());
        bookDto.setImageUrl(book.getImageUrl());
        
        return bookDto;
    }
	
    public boolean deleteBookById(Long id) {
        // Check if the book exists in the repository
        Optional<Book> existingBookOptional = bookrepo.findById(id);

        if (existingBookOptional.isPresent()) {
            // If the book exists, delete it
            bookrepo.deleteById(id);
            return true; // Deletion successful
        }

        return false; // Book not found
    }

    // Updated getBooksByAuthor to support pagination
    public Page<BookDto> getBooksByAuthor(String author, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookrepo.findByAuthor(author, pageable);

        // Convert Page<Book> to Page<BookDto>
        return booksPage.map(this::convertToBookDto);
    }

    // Updated getBooksByTitle to support pagination
    public Page<BookDto> getBooksByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookrepo.findByTitle(title, pageable);

        // Convert Page<Book> to Page<BookDto>
        return booksPage.map(this::convertToBookDto);
    }

        // Utility method to convert List<Book> to List<BookDto>
        private List<BookDto> convertToBookDtoList(List<Book> books) {
            List<BookDto> booksdto = new ArrayList<>();
            for (Book book : books) {
                booksdto.add(convertToBookDto(book));
            }
            return booksdto;
        }


}