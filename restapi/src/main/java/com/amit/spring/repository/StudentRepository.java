package com.amit.spring.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.amit.spring.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	List<Student> findByName(String name);
	List<Student> findByClassId(Integer classId);
	
	@Modifying
	@Query("update Student student set student.name = ?1 where student.id = ?2")
	void setStudentNameById(String name, Integer Id);
}
