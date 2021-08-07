package com.amit.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Mark {
    @Id
    @GeneratedValue(
    		strategy = GenerationType.AUTO
		)
	private int id;
    private int value;
    private String description;
    
    @ManyToOne
	@JoinColumn(
			name = "student_id",
			referencedColumnName="id",
			nullable=false,
			unique=true
		)
    private Student student;
}
