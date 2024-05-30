package com.example.jwt.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.entity.Book;
import com.example.jwt.entity.Borrowing;
import com.example.jwt.service.BookService;
import com.example.jwt.service.JwtService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
    private JwtService jwtService;
	
	@GetMapping("/api/search")
    public HashMap<String,Object> sarchBooks(@RequestParam String title) {
		
        HashMap<String,Object> response = new HashMap<>();
        
        response.put("Books", bookService.getBooksByTitle(title));
        
        return response;
        
    }

    @PostMapping("/api/addNewBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addNewBook(@RequestBody Book book
    		,@RequestHeader HashMap<String,String> headers) {
    	
    	try {
    		
    		book.setCreatedBy(jwtService
    				.extractUsername(headers.get("authorization").replace("Bearer ", "")));
    		
    		return ResponseEntity.ok(bookService.createNewBook(book));
    	}catch(Exception e) {
    		return ResponseEntity.internalServerError().body("Database Error");
    	}
        
    }
    
    @PostMapping("/api/updateBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateBook(@RequestBody Book book
    		,@RequestHeader HashMap<String,String> headers) {
        
        try {
        	
        	book.setUpdatedBy(jwtService
    				.extractUsername(headers.get("authorization").replace("Bearer ", "")));
    		
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
    
    @PostMapping("/api/addNewBorrowing")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addNewBorrowing(@RequestBody Borrowing borrowing
    		,@RequestHeader HashMap<String,String> headers) {
    	
    	try {
    		
    		borrowing.setCreatedBy(jwtService
    				.extractUsername(headers.get("authorization").replace("Bearer ", "")));
    		
    		return ResponseEntity.ok(bookService.createNewBorrowing(borrowing));
    	}catch(Exception e) {
    		return ResponseEntity.internalServerError().body("Database Error");
    	}
        
    }
    
    @PostMapping("/api/returnBorrowing")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> returnBorrowing(@RequestBody Borrowing borrowing
    		,@RequestHeader HashMap<String,String> headers) {
    	
    	try {
    		
    		//Get borrowing data by ID
    		borrowing = bookService.getBorrowingById(borrowing.getId());
    		
    		//Add updates data
    		borrowing.setUpdatedBy(jwtService
    				.extractUsername(headers.get("authorization").replace("Bearer ", "")));
    		borrowing.setReturnDate(LocalDate.now());
    		borrowing.setReturnTime(LocalTime.now());
    		
    		return ResponseEntity.ok(bookService.updateBorrowing(borrowing));
    	}catch(Exception e) {
    		return ResponseEntity.internalServerError().body("Database Error");
    	}
        
    }
    
    @GetMapping("/api/getAllBorrowing")
    
    public ResponseEntity<?> getAllBorrowing(@RequestHeader HashMap<String,String> headers) {
    	
    	try {
    		
    		return ResponseEntity.ok(bookService.getAllBorrowing());
    	}catch(Exception e) {
    		return ResponseEntity.internalServerError().body("Database Error");
    	}
        
    }
}
