package com.amit.redis.repository;

import org.springframework.data.repository.CrudRepository;

import com.amit.redis.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
