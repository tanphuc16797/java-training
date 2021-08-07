package com.amit.spring.service;

import com.amit.spring.domain.ClassDomain;
import com.amit.spring.domain.StudentDomain;
import com.amit.spring.model.Class;
import com.amit.spring.model.Student;
import com.amit.spring.model.request.CreateStudentRequest;
import com.amit.spring.model.request.EditStudentRequest;
import com.amit.spring.model.request.FindStudentByNameRequest;
import com.amit.spring.model.response.BaseResponse;
import com.amit.spring.model.utils.ApiException;
import com.amit.spring.model.utils.ERROR;
import com.amit.spring.repository.ClassRepository;
import com.amit.spring.repository.StudentRepository;

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
    StudentRepository studentRepository;
    @Autowired
    ClassRepository classRepository;

    public BaseResponse<List<Student>> getAllStudent(){
        BaseResponse<List<Student>> response = new BaseResponse<>();
        response.setData(studentRepository.findAll());
        return response;
    }
    
    public BaseResponse<Student> getStudent(int studentId) throws ApiException{
    	BaseResponse<Student> response = new BaseResponse<>();
    	Student student = studentRepository.findById(studentId).get();
        if (student == null){
            LOGGER.debug("StudentID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã sinh viên không tồn tại");
        }
        response.setData(student);
        return response;
    }
    
    public BaseResponse<List<Student>> getStudentByName(FindStudentByNameRequest request) throws ApiException{
    	BaseResponse<List<Student>> response = new BaseResponse<>();
    	List<Student> students = studentRepository.findByName(request.getName());
        response.setData(students);
        return response;
    }
    
    public BaseResponse<Student> createdStudent(CreateStudentRequest request) throws ApiException{
    	BaseResponse<Student> response = new BaseResponse<>();
    	
    	if (StringUtils.isBlank(request.getName())){
            LOGGER.debug("Studentname blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên của sinh viên không được để trống");
        }

        if (request.getClassId() == null){
            LOGGER.debug("StudentID blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã sinh viên không được để trống");
        }
        
        Class aClass = classRepository.findById(request.getClassId()).get();
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setAClass(aClass);
        studentRepository.save(student);
        
        response.setData(student);
        return response;
    }

    public BaseResponse<String> editedStudent(EditStudentRequest request, int studentId) throws ApiException{
    	Student student = studentRepository.findById(studentId).get();
        if (student == null){
            LOGGER.debug("StudentID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã sinh viên không tồn tại");
        }
        if (StringUtils.isBlank(request.getName())){
            LOGGER.debug("Studentname blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên của sinh viên không được để trống");
        }

        studentRepository.setStudentNameById(request.getName(), student.getId());
        return new BaseResponse<>();
    }
    
    public BaseResponse<String> deletedStudent(int studentId) throws ApiException{
    	Student student = studentRepository.findById(studentId).get();
        if (student == null){
            LOGGER.debug("StudentID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã sinh viên không tồn tại");
        }

        studentRepository.delete(student);
        return new BaseResponse<>();
    }
    
    
}
