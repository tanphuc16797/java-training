package com.example.controller;

import java.util.List;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.base.BaseResponse;
import com.example.management.StudentManagement;
import com.example.model.StudentModel;

public class StudentController {
	@RequestMapping(value="/students", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getAll(
			@DefaultValue("") @RequestParam("name") String name,
			@DefaultValue("-1") @RequestParam("id") int id
			) {
		StudentManagement student_management = new StudentManagement(null);
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
	
	@RequestMapping(value="/students/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getDetail(@PathVariable("id") int id) {
		StudentManagement student_management = new StudentManagement(null);
		StudentModel data = student_management.getDetail(id);
		return new BaseResponse(200, data, "");
	}
	
	@RequestMapping(value="/students/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse removeClass(@PathVariable("id") int id) {
		StudentManagement student_management = new StudentManagement(null);
		student_management.Delete(id);
		return new BaseResponse(200, "", "");
	}
}
