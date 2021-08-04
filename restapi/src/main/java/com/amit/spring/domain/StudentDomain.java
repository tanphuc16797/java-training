package com.amit.spring.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.amit.spring.model.Student;
import com.amit.spring.model.Class;


@Component
public class StudentDomain {
    private Map<String, Student> cacheByName = new HashMap<>();
    private Map<Integer, Student> cacheById = new HashMap<>();
    private static int IDD = 0;

    private synchronized int getIDD(){
        IDD ++;
        return IDD;
    }

    public Student findByName(String name){
        return cacheByName.get(name);
    }
    
    public Student findId(Integer id){
        return cacheById.get(id);
    }

    public List<Student> getAllStudent(){
        return new ArrayList<>(cacheById.values());
    }

    public void createStudent(String name, Class aClass){
        Student newStudent = new Student();
        newStudent.setId(this.getIDD());
        newStudent.setName(name);
        newStudent.setAClass(aClass);
        cacheByName.put(name , newStudent);
        cacheById.put(newStudent.getId() , newStudent);
    }

    public void deleteStudent(Student student){
        cacheById.remove(student.getId());
    }

    public void editStudent(Student student, String name){
        student.setName(name);
    }
}
