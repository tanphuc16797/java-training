package com.amit.spring.model;

import lombok.Data;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Data
@Entity
public class Class {
    @Id
    @GeneratedValue(
    		strategy = GenerationType.AUTO
		)
    private int id;
    private String name;
    
    @OneToMany(
    		mappedBy = "aClass",
    		cascade = CascadeType.ALL,
    		fetch = FetchType.LAZY
		)
    private Set<Student> students;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return id == aClass.id ;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
