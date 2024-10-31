package com.acum.schoolmanagement.controller;

import com.acum.schoolmanagement.dto.CourseSummary;
import com.acum.schoolmanagement.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseSummary> getCoursesWithStudentCount() {
        return courseService.getCoursesWithStudentCount();
    }
}
