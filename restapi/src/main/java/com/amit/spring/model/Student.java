package com.amit.spring.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;


@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(
    		strategy = GenerationType.AUTO
		)
    private int id;
    private String name;
    
    @OneToMany(
    		mappedBy = "student",
    		cascade = CascadeType.ALL,
    		fetch = FetchType.LAZY
		)
   private Set<Mark> mark;
    
    @ManyToOne
	@JoinColumn(
			name = "class_id",
			referencedColumnName="id",
			nullable=false,
			unique=true
	)
    private Class aClass;
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
