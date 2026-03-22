package com.example.CourseRegistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CourseRegistration.model.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUsername(String username);

}
