package com.example.management;

import java.util.ArrayList;
import java.util.List;
import com.example.base.BaseManagement;
import com.example.model.StudentModel;

public class StudentManagement extends BaseManagement<StudentModel>{

	public StudentManagement(List<StudentModel> items) {
		super(items);
		// TODO Auto-generated constructor stub
	}

	public List<StudentModel> findItems(String name) {
		// TODO Auto-generated method stub
		List<StudentModel> _items = new ArrayList<StudentModel>();
		for (StudentModel item : getItems()) {
			if (item.getName().equals(name)) {
				_items.add(item);
			}
		}
		return _items;
	}
	
	public List<StudentModel> findItems(int id) {
		// TODO Auto-generated method stub
		List<StudentModel> _items = new ArrayList<StudentModel>();
		for (StudentModel item : getItems()) {
			if (item.getId() == id) {
				_items.add(item);
			}
		}
		return _items;
	}
	
	public List<StudentModel> findItems(int id, String name) {
		// TODO Auto-generated method stub
		List<StudentModel> _items = new ArrayList<StudentModel>();
		for (StudentModel item : getItems()) {
			if (item.getId() == id && item.getName().equals(name)) {
				_items.add(item);
			}
		}
		return _items;
	}
	
	public List<StudentModel> findStudentByClassId(int class_id) {
		// TODO Auto-generated method stub
		List<StudentModel> _items = new ArrayList<StudentModel>();
		for (StudentModel item : getItems()) {
			if (item.getClass_id() == class_id) {
				_items.add(item);
			}
		}
		return _items;
	}
}
