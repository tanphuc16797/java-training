package com.example.controller;
import java.util.List;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.base.BaseResponse;
import com.example.data.DataStore;
import com.example.management.ClassManagement;
import com.example.management.StudentManagement;
import com.example.model.ClassModel;
import com.example.model.StudentModel;


@RestController
public class ClassController {

	@RequestMapping(value="/classes", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getAll(@DefaultValue("") @RequestParam("name") String name) {
		ClassManagement class_management = new ClassManagement(DataStore.classes);
		List<ClassModel> data;
		if (!name.equals("")) {
			data = class_management.getList();
		}else {
			data = class_management.findItems(name);
		}
		return new BaseResponse(200, data, "");
	}
	
	@RequestMapping(value="/classes/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getDetail(@PathVariable("id") int id) {
		ClassManagement class_management = new ClassManagement(DataStore.classes);
		ClassModel data = class_management.getDetail(id);
		return new BaseResponse(200, data, "");
	}
	
	@RequestMapping(value="/classes/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse removeClass(@PathVariable("id") int id) {
		ClassManagement class_management = new ClassManagement(DataStore.classes);
		class_management.Delete(id);
		return new BaseResponse(200, "", "");
	}
	
	@RequestMapping(value="/classes/{id}/students", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getStudentOfClass(@PathVariable("id") int id) {
		StudentManagement student_management = new StudentManagement(DataStore.students);
		List<StudentModel> data = student_management.findStudentByClassId(id);
		return new BaseResponse(200, data, "");
	}
}
