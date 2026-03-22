package com.example.CourseRegistration.Controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CourseRegistration.model.Course;
import com.example.CourseRegistration.repository.CourseRepository;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    @Autowired
    private CourseRepository courseRepository;

    // ✅ LIST + SEARCH + PAGINATION
    @GetMapping
    public String listCourses(Model model,
                             @RequestParam(defaultValue = "") String keyword,
                             @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Course> coursePage;

        if (!keyword.isEmpty()) {
            coursePage = courseRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else {
            coursePage = courseRepository.findAll(pageable);
        }

        model.addAttribute("coursePage", coursePage);
        model.addAttribute("keyword", keyword);

        return "admin/course-list";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/course-form";
    }

    // SAVE + TOAST
   @PostMapping("/save")
public String saveCourse(@ModelAttribute Course course,
                         @RequestParam("imageFile") MultipartFile file,
                         RedirectAttributes redirectAttributes) throws IOException {

    if (!file.isEmpty()) {

        String uploadDir = "src/main/resources/static/images/";
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        file.transferTo(new File(uploadDir + fileName));

        course.setImage(fileName);
    }

    courseRepository.save(course);

    redirectAttributes.addFlashAttribute("message", "Saved successfully!");
    return "redirect:/admin/courses";
}

    // EDIT
    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        model.addAttribute("course", course);

        return "admin/course-form";
    }

    // DELETE + TOAST
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id,
                              RedirectAttributes redirectAttributes) {

        courseRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Deleted successfully!");

        return "redirect:/admin/courses";
    }
}