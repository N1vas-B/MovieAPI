package com.book.store.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private long id;
	private String name;
	private String email;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private String roles;
	
}
