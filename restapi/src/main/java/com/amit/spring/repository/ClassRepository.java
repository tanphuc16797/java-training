package com.amit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.amit.spring.model.Class;


public interface ClassRepository extends JpaRepository<Class, Integer>{
	List<Class> findByName(String name);
	
	@Modifying
	@Query("update Class class set class.name = ?1 where class.id = ?2")
	void setClassNameById(String name, Integer Id);
}
