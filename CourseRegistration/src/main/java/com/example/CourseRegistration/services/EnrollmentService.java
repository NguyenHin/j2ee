package com.example.CourseRegistration.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CourseRegistration.model.Enrollment;
import com.example.CourseRegistration.model.Student;
import com.example.CourseRegistration.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void enroll(Enrollment enrollment){
        enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getCoursesByStudent(Student student){
        return enrollmentRepository.findByStudent(student);
    }

}
