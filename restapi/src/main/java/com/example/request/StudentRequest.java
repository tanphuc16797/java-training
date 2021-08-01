package com.example.request;

public class StudentRequest {
	public String name;
	
	public static class CreateBody{
		public String name;
		public String code;
		public int class_id;
	}
	
	public static class UpdateBody{
		public String name;
		public String code;
		public int class_id;
	} 
}
