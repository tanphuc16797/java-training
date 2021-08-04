package com.amit.spring.domain;

import com.amit.spring.model.Class;
import com.amit.spring.model.Student;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ClassDomain {
    private Map<String, Class> cacheByName = new HashMap<>();
    private Map<Integer, Class> cacheById = new HashMap<>();
    private static int IDD = 0;

    private synchronized int getIDD(){
        IDD ++;
        return IDD;
    }

    public Class findByName(String name){
        return cacheByName.get(name);
    }
    
    public Class findId(Integer id){
        return cacheById.get(id);
    }

    public List<Class> getAllClass(){
        return new ArrayList<>(cacheById.values());
    }
        
    public void createClass(String name){
        Class newClass = new Class();
        newClass.setId(this.getIDD());
        newClass.setName(name);
        newClass.setStudents(new HashSet<Student>());
        cacheByName.put(name , newClass);
        cacheById.put(newClass.getId() , newClass);
    }

    public void deleteClass(Class aClass){
        cacheById.remove(aClass.getId());
    }

    public void editClass(Class aClass, String name){
    	aClass.setName(name);
    }
}
