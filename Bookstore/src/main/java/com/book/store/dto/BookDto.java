package com.book.store.dto;

import lombok.Data;

@Data
public class BookDto {

	private Long id;
	private String title;
    private String author;
    private String genre;
    private String isbn;
    private Double price;
    private String description;
    private Integer stockQuantity;
    private String imageUrl;
}
