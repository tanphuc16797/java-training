package com.amit.spring.controller;

import com.amit.spring.model.Class;
import com.amit.spring.model.Student;
import com.amit.spring.model.request.*;
import com.amit.spring.model.response.BaseResponse;
import com.amit.spring.model.utils.ApiException;
import com.amit.spring.service.ClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping
    public BaseResponse<List<Class>> getAllClass(
	) throws ApiException{return classService.getAllClass();}

    @GetMapping("/{id}")
    public BaseResponse<Class> getClass(
    		@PathVariable("id") int classId
    ) throws ApiException{return classService.getClass(classId);}
    
    @GetMapping("/find-by-name")
    public BaseResponse<List<Class>> getClassByName(
    		@RequestParam FindClassByNameRequest request
    ) throws ApiException{return classService.getClassByName(request);}

    @PostMapping
    public BaseResponse<Class> createClass(
    		@RequestBody CreateClassRequest request
    ) throws ApiException{return classService.createdClass(request);}

    @PutMapping("/{id}")
    public BaseResponse<String> editClass(
    		@PathVariable("id") int classId,
    		@RequestBody EditClassRequest request
    ) throws ApiException{return classService.editedClass(request, classId);}
    
    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteClass(
    		@PathVariable("id") int classId
    ) throws ApiException{return classService.deletedClass(classId);}

    @GetMapping("/{id}/student")
    public BaseResponse<List<Student>> getStudentOfClass(
    		@PathVariable("id") int classId
    ) throws ApiException{return classService.getStudentOfClass(classId);}

    @GetMapping("/{id}/get-total-mark")
    public BaseResponse<Integer> getTotalMarkOfClass(
    		@PathVariable("id") int classId
    ) throws ApiException{return classService.getTotalMarkOfClass(classId);}

}
