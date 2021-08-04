package com.amit.spring.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.amit.spring.model.Student;
import com.amit.spring.model.Mark;


@Component
public class MarkDomain {
    private Map<Integer, Mark> cacheById = new HashMap<>();
    private static int IDD = 0;

    private synchronized int getIDD(){
        IDD ++;
        return IDD;
    }

    public Mark findId(Integer id){
        return cacheById.get(id);
    }

    public Mark findStudent(Student student){
        for (Mark mark : getAllMark()) {
        	if (mark.getStudent().equals(student)) {
        		return mark;
        	}
        }
        return null;
    }
    
    public List<Mark> getAllMark(){
        return new ArrayList<>(cacheById.values());
    }

    public void createMark(Student student){
        Mark mark = new Mark();
        mark.setId(this.getIDD());
        mark.setStudent(student);
        cacheById.put(mark.getId() , mark);
    }

    public void deleteStudent(Mark mark){
        cacheById.remove(mark.getId());
    }

}
