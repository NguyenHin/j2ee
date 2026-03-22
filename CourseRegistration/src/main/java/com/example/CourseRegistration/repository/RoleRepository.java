package com.example.CourseRegistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CourseRegistration.model.Role;
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
