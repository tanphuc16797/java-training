package com.example.service;

import java.util.List;

public interface CRUDService <T> {
	T getDetail(int id);
	List<T> getList();
	void Create(T instance);
	void Update(T old_instance, T new_instance);
	void Delete(int id);
}
