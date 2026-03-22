package com.example.CourseRegistration.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CourseRegistration.model.Enrollment;
import com.example.CourseRegistration.model.Student;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudent(Student student);

}