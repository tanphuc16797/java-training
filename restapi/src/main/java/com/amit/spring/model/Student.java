package com.amit.spring.model;

import lombok.Data;

@Data
public class Student {
    private int id;
    private String name;
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
