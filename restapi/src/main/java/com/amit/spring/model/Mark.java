package com.amit.spring.model;

import lombok.Data;

@Data
public class Mark {
	private int id;
    private int value;
    private String description;
    private Student student;
}
