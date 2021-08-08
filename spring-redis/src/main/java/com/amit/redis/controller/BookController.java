package com.amit.redis.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amit.redis.service.BookService;
import com.amit.redis.controller.response.BaseResponse;
import com.amit.redis.model.Book;
import com.amit.redis.controller.exception.ApiException;
import com.amit.redis.controller.request.AddBookRequest;
import com.amit.redis.controller.request.EditBookRequest;

@RequestMapping("/books")
public class BookController {

	@Autowired
    BookService bookService;
	
    @GetMapping
    public BaseResponse<List<Book>> getAllBook(
	) throws ApiException{
    	return bookService.getAll();
    }

    @PostMapping()
    public BaseResponse<Book> addBook(
    		@RequestBody AddBookRequest request
	) throws ApiException{
    	return bookService.addBook(request);
    }
    
    @GetMapping("/{id}")
    public BaseResponse<Book> getBook(
    		@PathVariable int id
	) throws ApiException{
    	return bookService.getBookById(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> deleleBook(
    		@PathVariable int id
	) throws ApiException{
    	return bookService.deleteBookById(id);
    }

    @PutMapping("/{id}")
    public BaseResponse<Book> editBook(
    		@PathVariable int id,
    		@RequestBody EditBookRequest request
	) throws ApiException{
    	return bookService.editBook(request,id);
    }
    
}
