package com.example.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.entity.Book;
import com.example.jwt.entity.Borrowing;
import com.example.jwt.repository.BookRepository;
import com.example.jwt.repository.BorrowingRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BorrowingRepository borrowingRepository;
	
	public Book createNewBook(Book book) {
		
		return bookRepository.save(book);
	}
	
	public Borrowing createNewBorrowing(Borrowing borrowing) {
		
		return borrowingRepository.save(borrowing);
	}
	
	public Book updateBook(Book book) {
		
		return bookRepository.save(book);
	}
	
	public Borrowing updateBorrowing(Borrowing borrowing) {
		
		return borrowingRepository.save(borrowing);
	}
	
	public void deleteBook(Book book) {
		
		bookRepository.delete(book);
	}
	
	public List<Book> getBooksByTitle(String title) {
		
		return bookRepository.findByTitle(title).orElse(null);
	}
	
	public Borrowing getBorrowingById(int id) {
		
		return borrowingRepository.findById(id).orElse(null);
	}
}
