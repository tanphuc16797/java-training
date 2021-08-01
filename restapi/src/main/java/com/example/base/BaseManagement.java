package com.example.base;
import java.util.List;

import com.example.service.CRUDService;

public class BaseManagement <T> implements CRUDService<T> {
	private List<T> items;

	public BaseManagement(List<T> items) {
		// TODO Auto-generated constructor stub
		this.items = items;
	}
	
	public T getDetail(int id) {
		// TODO Auto-generated method stub
		for (T item : getItems()) {
			BaseModel base_item = (BaseModel) item;
			if (base_item.getId() == id) {
				return item;
			}
		}
		return null;
	}
	
	public List<T> getList() {
		// TODO Auto-generated method stub
		return this.getItems();
	}

	public void Create(T instance) {
		// TODO Auto-generated method stub
		items.add(instance);
	}

	public void Update(T old_instance, T new_instance) {
		// TODO Auto-generated method stub
		BaseModel old_item = (BaseModel) old_instance;
		for (T item : getItems()) {
			BaseModel base_item = (BaseModel) item;
			if (base_item.getId() == old_item.getId()) {
				item = new_instance;
			}
		}
	}

	public void Delete(int id) {
		// TODO Auto-generated method stub
		for (T item : getItems()) {
			BaseModel base_item = (BaseModel) item;
			if (base_item.getId() == id) {
				items.remove(item);
				break;
			}
		}
	}

	public int getNewId() {
		// TODO Auto-generated method stub
		int max_id = 0;
		for (T item : getItems()) {
			BaseModel base_item = (BaseModel) item;
			if (base_item.getId() > max_id) {
				max_id = base_item.getId();
			}
		}
		return max_id;
	}
	
	public List<T> getItems() {
		return items;
	}
		
}
