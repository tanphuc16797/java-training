package com.amit.spring.model.request;

import lombok.Data;

@Data
public class CreateStudentRequest {
    private String name;
    private Integer classId;
}
