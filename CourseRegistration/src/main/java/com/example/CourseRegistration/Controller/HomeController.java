package com.example.CourseRegistration.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.CourseRegistration.model.Course;
import com.example.CourseRegistration.repository.CourseRepository;

@Controller
public class HomeController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping({"/","/home"})
    public String home(Model model,
                       @RequestParam(defaultValue = "0") int page){

        Page<Course> coursePage =
                courseRepository.findAll(PageRequest.of(page,5));

        model.addAttribute("courses", coursePage);

        return "home";
    }

}
