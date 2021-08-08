package com.amit.redis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.amit.redis.controller.exception.ApiException;
import com.amit.redis.controller.error.ERROR;
import com.amit.redis.controller.request.AddBookRequest;
import com.amit.redis.controller.request.EditBookRequest;
import com.amit.redis.controller.response.BaseResponse;
import com.amit.redis.repository.BookRepository;
import com.amit.redis.model.Book;
import com.amit.redis.queue.MessagePublisher;

@Service
public class BookServiceImpl implements BookService {
    public static int id = 0;

    @Autowired
    BookRepository BookRepository;

    @Autowired
    MessagePublisher messagePublisher;
    
    @Autowired
    RedisTemplate<Integer, Object> redisTemplate;
    
    
    @Override
    public BaseResponse<Book> addBook(AddBookRequest request) {
    	BaseResponse<Book> response = new BaseResponse<>();
        
    	Book book = new Book();
        book.setName(request.getName());
        book.setDescription(request.getDescription());
        book.setId(BookServiceImpl.id + 1);
        BookRepository.save(book);

        response.setData(book);
        BookServiceImpl.id += 1;
        messagePublisher.publish("add Book name :"+  request.getName());
        
        return response;
    }

    @Override
    public BaseResponse<Book> getBookById(int id) {
        Book book = BookRepository.findById(id).get();
        if (book == null)
			throw new ApiException(ERROR.INVALID_PARAM , "Mã sách không tồn tại");
		
        BaseResponse<Book> response = new BaseResponse<>();
        messagePublisher.publish("get Book info by ID :"+  id);
        if (book != null)
            response.setData(book);
        return response;
    }

    @Override
    public BaseResponse<String> deleteBookById(int id) {
    	Book book = BookRepository.findById(id).get();
    	if (book == null)
			throw new ApiException(ERROR.INVALID_PARAM , "Mã sách không tồn tại");
		
    	BookRepository.deleteById(id);
        BaseResponse<String> response = new BaseResponse<>();
        return response;
    }

    @Override
    public BaseResponse<List<Book>> getAll() {
        Iterable<Book> Book = BookRepository.findAll();
        BaseResponse<List<Book>> response = new BaseResponse<>();
        response.setData((List<com.amit.redis.model.Book>) Book);
        return response;

    }

	@Override
	public BaseResponse<Book> editBook(EditBookRequest request, int id) {
		// TODO Auto-generated method stub
		BaseResponse<Book> response = new BaseResponse<>();
		Book book = BookRepository.findById(id).get();
		if (book == null)
			throw new ApiException(ERROR.INVALID_PARAM , "Mã sách không tồn tại");
		
		book.setName(request.getName());
		BookRepository.save(book);
		response.setData(book);
		return response;
	}
}