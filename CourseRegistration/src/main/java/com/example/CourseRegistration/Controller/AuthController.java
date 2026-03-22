package com.example.CourseRegistration.Controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.CourseRegistration.model.Role;
import com.example.CourseRegistration.model.Student;
import com.example.CourseRegistration.repository.RoleRepository;
import com.example.CourseRegistration.repository.StudentRepository;

@Controller
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student){

        // encode password
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        // role STUDENT
        Role role = roleRepository.findByName("STUDENT");

        student.setRoles(Set.of(role));

        studentRepository.save(student);

        return "redirect:/login";
    }
}