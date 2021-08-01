package com.example.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.base.BaseResponse;
import com.example.data.DataStore;
import com.example.management.ClassManagement;
import com.example.management.StudentManagement;
import com.example.model.ClassModel;
import com.example.model.StudentModel;
import com.example.request.ClassRequest;


@RestController
public class ClassController {
	@Autowired
    private ClassManagement class_management = new ClassManagement(DataStore.classes);
	@Autowired
	StudentManagement student_management = new StudentManagement(DataStore.students);
	
	@RequestMapping(value="/classes", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getAll(@DefaultValue("") @RequestParam("name") String name) {
		List<ClassModel> data;
		if (!name.equals("")) {
			data = class_management.getList();
		}else {
			data = class_management.findItems(name);
		}
		return new BaseResponse(200, data, "");
	}
	
	@RequestMapping(value="/classes", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse newClass(
			@RequestBody  ClassRequest.CreateBody class_request
			) {
		ClassModel new_class = new ClassModel();
		new_class.setId(class_management.getNewId());
		new_class.setName(class_request.name);
		class_management.Create(new_class);
		return new BaseResponse(200, new_class, "");
	}
	
	@RequestMapping(value="/classes/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getDetail(@PathVariable("id") int id) {
		ClassModel data = class_management.getDetail(id);
		return new BaseResponse(200, data, "");
	}
	
	@RequestMapping(value="/classes/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public BaseResponse updateDetail(
			@PathVariable("id") int id,
			@RequestBody  ClassRequest.UpdateBody class_request
			) {
		StudentModel old_class = student_management.getDetail(id);
		StudentModel new_class = new StudentModel();
		new_class.setName(class_request.name);
		student_management.Update(old_class, new_class);
		return new BaseResponse(200, new_class, "");
	}
	
	@RequestMapping(value="/classes/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public BaseResponse removeClass(@PathVariable("id") int id) {
		class_management.Delete(id);
		return new BaseResponse(200, "", "");
	}
	
	@RequestMapping(value="/classes/{id}/students", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse getStudentOfClass(@PathVariable("id") int id) {
		List<StudentModel> data = student_management.findStudentByClassId(id);
		return new BaseResponse(200, data, "");
	}
}
