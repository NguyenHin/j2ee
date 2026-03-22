package com.example.CourseRegistration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CourseRegistration.model.Student;
import com.example.CourseRegistration.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student findByUsername(String username){
        return studentRepository.findByUsername(username);
    }

    public void save(Student student){
        studentRepository.save(student);
    }

}