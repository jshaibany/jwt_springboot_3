package com.example.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.entity.Book;
import com.example.jwt.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public Book createNewBook(Book book) {
		
		return bookRepository.save(book);
	}
	
	public Book updateBook(Book book) {
		
		return bookRepository.save(book);
	}
	
	public void deleteBook(Book book) {
		
		bookRepository.delete(book);
	}
	
	public List<Book> getBooksByTitle(String title) {
		
		return bookRepository.findByTitle(title).orElse(null);
	}
}
