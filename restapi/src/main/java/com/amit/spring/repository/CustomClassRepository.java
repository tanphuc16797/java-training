package com.amit.spring.repository;


import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amit.spring.model.Class;


@Repository
public class CustomClassRepository {
	@Autowired
    private EntityManager entityManager;
      
 
    public CustomClassRepository() {
    }
    
    public Optional<Class> findById(Integer id) { 
    	Class aClass = this.entityManager.find(Class.class, id);
    	return Optional.of(aClass);
    }
    
    public void deleteById(Integer id) {
    	Class aClass = findById(id).get();
    	this.entityManager.remove(aClass);
    }
    
}