package com.example.jwt.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.entity.Book;
import com.example.jwt.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/api/search")
    public HashMap<String,Object> sarchBooks(@RequestParam String title) {
		
        HashMap<String,Object> response = new HashMap<>();
        
        response.put("Books", bookService.getBooksByTitle(title));
        
        return response;
        
    }

    @PostMapping("/api/addNewBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addNewBook(@RequestBody Book book) {
    	
    	try {
    		return ResponseEntity.ok(bookService.createNewBook(book));
    	}catch(Exception e) {
    		return ResponseEntity.internalServerError().body("Database Error");
    	}
        
    }
    
    @PostMapping("/api/updateBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        
        try {
    		return ResponseEntity.ok(bookService.updateBook(book));
    	}catch(Exception e) {
    		return ResponseEntity.internalServerError().body("Database Error");
    	}
    }
    
    @PostMapping("/api/deleteBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteBook(@RequestBody Book book) {
    	
    	

        try {
        	bookService.deleteBook(book);
    		return ResponseEntity.ok("Book Deleted");
    	}catch(Exception e) {
    		return ResponseEntity.internalServerError().body("Database Error");
    	}
    }
}
