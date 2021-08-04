package com.amit.spring.service;

import com.amit.spring.domain.ClassDomain;
import com.amit.spring.domain.MarkDomain;
import com.amit.spring.domain.StudentDomain;
import com.amit.spring.model.Class;
import com.amit.spring.model.Mark;
import com.amit.spring.model.Student;
import com.amit.spring.model.request.*;
import com.amit.spring.model.response.BaseResponse;
import com.amit.spring.model.utils.ApiException;
import com.amit.spring.model.utils.ERROR;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ClassService {
    private static final Logger LOGGER = LogManager.getLogger(ClassService.class);

    @Autowired
    ClassDomain classDomain;
    
    @Autowired
    StudentDomain studentDomain;
    
    @Autowired
    MarkDomain markDomain;

    public BaseResponse<List<Class>> getAllClass(){
        BaseResponse<List<Class>> response = new BaseResponse<>();
        response.setData(classDomain.getAllClass());
        return response;
    }

    public BaseResponse<Class> getClass(int classId) throws ApiException{
    	BaseResponse<Class> response = new BaseResponse<>();
        Class aClass = classDomain.findId(classId);
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }
        response.setData(aClass);
        return response;
    }
    
    public BaseResponse<Class> getClassByName(FindClassByNameRequest request) throws ApiException{
    	BaseResponse<Class> response = new BaseResponse<>();
        Class aClass = classDomain.findByName(request.getName());
        if (aClass == null){
            LOGGER.debug("ClassName is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên lớp không tồn tại");
        }
        response.setData(aClass);
        return response;
    }
    
    public BaseResponse<String> createdClass(CreateClassRequest request) throws ApiException{
        if (StringUtils.isBlank(request.getName())){
            LOGGER.debug("Classname blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên của lớp không được để trống");
        }

        if (classDomain.findByName(request.getName()) != null){
            LOGGER.debug("Classname {} existed" , request.getName());
            throw new ApiException(ERROR.CLASS_NAME_EXIST);
        }

        classDomain.createClass(request.getName());
        return new BaseResponse<>();
    }

    public BaseResponse<String> deletedClass(int classId) throws ApiException{
        Class aClass = classDomain.findId(classId);
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }

        classDomain.deleteClass(aClass);
        return new BaseResponse<>();
    }
    
    public BaseResponse<String> editedClass(EditClassRequest request, int classId) throws ApiException{
        Class aClass = classDomain.findId(classId);
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }
        if (StringUtils.isBlank(request.getName())){
            LOGGER.debug("Classname blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên của lớp không được để trống");
        }

        classDomain.editClass(aClass, request.getName());
        return new BaseResponse<>();
    }
   
    public BaseResponse<List<Student>> getStudentOfClass(int classId) throws ApiException{
    	BaseResponse<List<Student>> response = new BaseResponse<>();
    	Class aClass = classDomain.findId(classId);
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }
        
        Predicate<Student> studentOfClass 
        	= student -> student.getAClass().equals(aClass);
        
        List<Student> students = studentDomain.getAllStudent();
        List<Student> filteredList = students.stream()
							        		 .filter(studentOfClass)
							        		 .collect(Collectors.toList());
        response.setData(filteredList);
        return new BaseResponse<>();
    }

    public BaseResponse<Integer> getTotalMarkOfClass(int classId) throws ApiException{
    	BaseResponse<Integer> response = new BaseResponse<>();
    	Class aClass = classDomain.findId(classId);
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }

        Integer totalScore = 0;
        List<Student> students = studentDomain.getAllStudent();
        for (Student student: students) {
        	if (student.getAClass().equals(aClass)){
        		Mark mark = markDomain.findStudent(student);
        		if (mark != null) {
        			totalScore = totalScore + mark.getValue();
        		}
        	}
        }
        response.setData(totalScore);
        return new BaseResponse<>();
    }
}
