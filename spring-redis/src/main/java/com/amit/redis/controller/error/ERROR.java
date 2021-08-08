package com.amit.redis.controller.error;

/**
 * chứa code và message trả về khi response
 */
public enum ERROR {
    SUCCESS(1, "success"),
    SYSTEM_ERROR(99 , "Hệ thống đang bảo trì , xin vui lòng thử lại sau!"),
    INVALID_PARAM(10, "Tham số truyền lên không hợp lệ"),
    CLASS_NAME_EXIST(100, "Tên của lớp đã được sử dụng"),
 ;

 private int code;
 private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ERROR(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
