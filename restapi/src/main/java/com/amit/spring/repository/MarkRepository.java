package com.amit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.spring.model.Mark;

public interface MarkRepository extends JpaRepository<Mark, Integer>{
}
