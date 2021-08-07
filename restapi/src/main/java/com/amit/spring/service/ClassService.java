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
import com.amit.spring.repository.ClassRepository;
import com.amit.spring.repository.StudentRepository;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class ClassService {
    private static final Logger LOGGER = LogManager.getLogger(ClassService.class);

    @Autowired
    ClassRepository classRepository;
    
    @Autowired
    StudentRepository studentRepository;
    

    public BaseResponse<List<Class>> getAllClass(){
        BaseResponse<List<Class>> response = new BaseResponse<>();
        response.setData(classRepository.findAll());
        return response;
    }

    public BaseResponse<Class> getClass(int classId) throws ApiException{
    	BaseResponse<Class> response = new BaseResponse<>();
        Class aClass = classRepository.findById(classId).get();
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }
        response.setData(aClass);
        return response;
    }
    
    public BaseResponse<List<Class>> getClassByName(FindClassByNameRequest request) throws ApiException{
    	BaseResponse<List<Class>> response = new BaseResponse<>();
    	List<Class> classes = classRepository.findByName(request.getName());
        response.setData(classes);
        return response;
    }
    
    public BaseResponse<Class> createdClass(CreateClassRequest request) throws ApiException{
    	BaseResponse<Class> response = new BaseResponse<>();
    	if (StringUtils.isBlank(request.getName())){
            LOGGER.debug("Classname blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên của lớp không được để trống");
        }
        
        Class aClass = new ModelMapper().map(request, Class.class);
        aClass = classRepository.save(aClass);
        response.setData(aClass);
        return response;
    }

    public BaseResponse<String> deletedClass(int classId) throws ApiException{
        Class aClass = classRepository.findById(classId).get();
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }

        classRepository.deleteById(aClass.getId());
        return new BaseResponse<>();
    }
    
    public BaseResponse<String> editedClass(EditClassRequest request, int classId) throws ApiException{
        Class aClass = classRepository.findById(classId).get();
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }
        if (StringUtils.isBlank(request.getName())){
            LOGGER.debug("Classname blank" );
            throw new ApiException(ERROR.INVALID_PARAM , "Tên của lớp không được để trống");
        }

        classRepository.setClassNameById(request.getName(), aClass.getId());
        return new BaseResponse<>();
    }
   
    public BaseResponse<List<Student>> getStudentOfClass(int classId) throws ApiException{
    	BaseResponse<List<Student>> response = new BaseResponse<>();
    	Class aClass = classRepository.findById(classId).get();
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }
        
        List<Student> students = studentRepository.findByClassId(aClass.getId());
        
        response.setData(students);
        return response;
    }

    public BaseResponse<Integer> getTotalMarkOfClass(int classId) throws ApiException{
    	BaseResponse<Integer> response = new BaseResponse<>();
    	Class aClass = classRepository.findById(classId).get();
        if (aClass == null){
            LOGGER.debug("ClassID is not existed" );
            throw new ApiException(ERROR.INVALID_PARAM , "Mã lớp không tồn tại");
        }

        Integer totalScore = 0;
        
        Set<Student> students = aClass.getStudents();
        for (Student student: students) {
        	Set<Mark> marks = student.getMark();
    		
        	if (!marks.isEmpty()){
        		for (Mark mark: marks){
        			totalScore = totalScore + mark.getValue();
        		}
        	}
        }
        response.setData(totalScore);
        return new BaseResponse<>();
    }
}
