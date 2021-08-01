package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.base.BaseResponse;
import com.example.data.DataStore;
import com.example.management.StudentManagement;
import com.example.model.StudentModel;
import com.example.request.StudentRequest;

@RestController
public class StudentController {
	@Autowired
	StudentManagement student_management = new StudentManagement(DataStore.students);

	
	@RequestMapping(value="/students", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getAll(
			@DefaultValue("") @RequestParam("name") String name,
			@DefaultValue("-1") @RequestParam("id") int id
			) {
		List<StudentModel> data;
		if (!name.equals("") && id == -1) {
			if (name.equals("") && id != -1) {
				data = student_management.findItems(id, name);
			}else {
				if(!name.equals("")) {
					data = student_management.findItems(name);
				}else {
					data = student_management.findItems(id);
				}
			}
		}else {
			data = student_management.findItems(name);
		}
		return new BaseResponse(200, data, "");
	}
	
	@RequestMapping(value="/student", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse newClass(
			@RequestBody  StudentRequest.CreateBody student_request
			) {
		StudentModel new_student = new StudentModel();
		new_student.setId(student_management.getNewId());
		new_student.setName(student_request.name);
		student_management.Create(new_student);
		return new BaseResponse(200, new_student, "");
	}
	
	@RequestMapping(value="/students/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getDetail(@PathVariable("id") int id) {
		StudentModel data = student_management.getDetail(id);
		return new BaseResponse(200, data, "");
	}
	
	@RequestMapping(value="/students/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public BaseResponse updateDetail(
			@PathVariable("id") int id,
			@RequestBody  StudentRequest.UpdateBody student_request
			) {
		StudentModel old_student = student_management.getDetail(id);
		StudentModel new_student = new StudentModel();
		new_student.setCode(student_request.code);
		new_student.setName(student_request.name);
		new_student.setClass_id(student_request.class_id);
		student_management.Update(old_student, new_student);
		return new BaseResponse(200, new_student, "");
	}
	
	@RequestMapping(value="/students/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse removeClass(@PathVariable("id") int id) {
		student_management.Delete(id);
		return new BaseResponse(200, "", "");
	}
}
