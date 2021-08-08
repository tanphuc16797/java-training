package com.amit.redis.service;

import java.util.List;

import com.amit.redis.controller.request.AddBookRequest;
import com.amit.redis.controller.request.EditBookRequest;
import com.amit.redis.controller.response.BaseResponse;
import com.amit.redis.model.Book;

public interface BookService {
    BaseResponse<Book> addBook(AddBookRequest request);
    BaseResponse<Book> getBookById(int id);
    BaseResponse<String> deleteBookById(int id);
    BaseResponse<List<Book>> getAll();
	BaseResponse<Book> editBook(EditBookRequest request, int id);
}
