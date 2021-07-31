package com.example.management;

import java.util.ArrayList;
import java.util.List;

import com.example.base.BaseManagement;
import com.example.model.ClassModel;

public class ClassManagement extends BaseManagement<ClassModel>{

	public ClassManagement(List<ClassModel> items) {
		super(items);
		// TODO Auto-generated constructor stub
	}

	public ClassModel getDetail(String name) {
		// TODO Auto-generated method stub
		for (ClassModel item : getItems()) {
			if (item.getName().equals(name)) {
				return item;
			}
		}
		return null;
	}
	
	public List<ClassModel> findItems(String name) {
		// TODO Auto-generated method stub
		List<ClassModel> _items = new ArrayList<ClassModel>();
		for (ClassModel item : getItems()) {
			if (item.getName().equals(name)) {
				_items.add(item);
			}
		}
		return _items;
	}
}
