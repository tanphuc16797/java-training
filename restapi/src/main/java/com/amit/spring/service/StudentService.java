package com.amit.spring.service;

import com.amit.spring.domain.ClassDomain;
import com.amit.spring.domain.StudentDomain;
import com.amit.spring.model.Class;
import com.amit.spring.model.Student;
import com.amit.spring.model.request.CreateClassRequest;
import com.amit.spring.model.request.CreateStudentRequest;
import com.amit.spring.model.request.EditClassRequest;
import com.amit.spring.model.request.EditStudentRequest;
import com.amit.spring.model.request.FindClassByNameRequest;
import com.amit.spring.model.request.FindStudentByNameRequest;
import com.amit.spring.model.response.BaseResponse;
import com.amit.spring.model.utils.ApiException;
import com.amit.spring.model.utils.ERROR;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StudentService {
    private static final Logger LOGGER = LogManager.getLogger(StudentService.class);

    @Autowired
    StudentDomain studentDomain;
    @Autowired
    ClassDomain classDomain;

    public BaseResponse<List<Student>> getAllStudent(){
        BaseResponse<List<Student>> response = new BaseResponse<>();
        response.setData(studentDomain.getAllStudent());
        return response;
    }
    
    public BaseResponse<Student> getStudent(int studentId) throws ApiException{
    	BaseResponse<Student> response = new BaseResponse<>();
    	Student student = studentDomain.findId(studentId);
        if (student == null){
            LOGGER.debug("StudentID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã sinh viên không tồn tại");
        }
        response.setData(student);
        return response;
    }
    
    public BaseResponse<Student> getStudentByName(FindStudentByNameRequest request) throws ApiException{
    	BaseResponse<Student> response = new BaseResponse<>();
    	Student student = studentDomain.findByName(request.getName());
        if (student == null){
            LOGGER.debug("ClassName is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên lớp không tồn tại");
        }
        response.setData(student);
        return response;
    }
    
    public BaseResponse<String> createdStudent(CreateStudentRequest request) throws ApiException{
        if (StringUtils.isBlank(request.getName())){
            LOGGER.debug("Studentname blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên của sinh viên không được để trống");
        }

        if (request.getClassId() == null){
            LOGGER.debug("StudentID blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã sinh viên không được để trống");
        }
        
        Class aClass = classDomain.findId(request.getClassId());
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }

        studentDomain.createStudent(request.getName(), aClass);
        return new BaseResponse<>();
    }

    public BaseResponse<String> editedStudent(EditStudentRequest request, int studentId) throws ApiException{
    	Student student = studentDomain.findId(studentId);
        if (student == null){
            LOGGER.debug("StudentID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã sinh viên không tồn tại");
        }
        if (StringUtils.isBlank(request.getName())){
            LOGGER.debug("Studentname blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên của sinh viên không được để trống");
        }

        studentDomain.editStudent(student, request.getName());
        return new BaseResponse<>();
    }
    
    public BaseResponse<String> deletedStudent(int id) throws ApiException{
        Student student = studentDomain.findId(id);
        if (student == null){
            LOGGER.debug("StudentID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã sinh viên không tồn tại");
        }

        studentDomain.deleteStudent(student);
        return new BaseResponse<>();
    }
    
    
}
