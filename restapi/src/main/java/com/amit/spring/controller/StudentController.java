package com.amit.spring.controller;

import com.amit.spring.model.Student;
import com.amit.spring.model.request.*;
import com.amit.spring.model.response.BaseResponse;
import com.amit.spring.model.utils.ApiException;
import com.amit.spring.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/student")
public class StudentController {
    @Autowired
    private StudentService studentService;


    @GetMapping
    public BaseResponse<List<Student>> getAllClass(
	) throws ApiException{return studentService.getAllStudent();}

    @GetMapping("/{id}")
    public BaseResponse<Student> getStudent(
    		@PathVariable("id") int studentId
    ) throws ApiException{return studentService.getStudent(studentId);}
    
    @GetMapping("/find-by-name")
    public BaseResponse<Student> getClassByName(
    		@RequestParam FindStudentByNameRequest request
    ) throws ApiException{return studentService.getStudentByName(request);}

    @PostMapping
    public BaseResponse<String> createClass(
    		@RequestBody CreateStudentRequest request
    ) throws ApiException{return studentService.createdStudent(request);}

    @PutMapping("/{id}")
    public BaseResponse<String> editClass(
    		@PathVariable("id") int studentId,
    		@RequestBody EditStudentRequest request
    ) throws ApiException{return studentService.editedStudent(request, studentId);}
    
    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteClass(
    		@PathVariable("id") int studentId
    ) throws ApiException{return studentService.deletedStudent(studentId);}

}
