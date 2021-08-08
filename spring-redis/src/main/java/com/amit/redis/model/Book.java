package com.amit.redis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash("book")
public class Book {
	@Id
	private int id;
	private String name;
	private String description;
}
