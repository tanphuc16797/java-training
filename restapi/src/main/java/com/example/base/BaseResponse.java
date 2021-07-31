package com.example.base;


public class BaseResponse {
	private int status;
	private Object data;
	private String message;

	public BaseResponse(int status, Object data, String message) {
		// TODO Auto-generated constructor stub
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}
	
}
