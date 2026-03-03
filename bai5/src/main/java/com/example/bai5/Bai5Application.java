package com.example.bai5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Bai5Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Bai5Application.class, args);
    }

    // Cấu hình để đọc ảnh trực tiếp từ thư mục src (hiển thị ngay không cần restart)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:src/main/resources/static/images/");
    }
}