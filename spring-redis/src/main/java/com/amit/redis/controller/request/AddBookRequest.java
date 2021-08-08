package com.amit.redis.controller.request;

import lombok.Data;

@Data
public class AddBookRequest {
	private String name;
	private String description;
}
