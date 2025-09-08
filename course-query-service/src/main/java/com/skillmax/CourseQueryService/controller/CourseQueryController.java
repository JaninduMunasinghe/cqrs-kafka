package com.skillmax.CourseQueryService.controller;

import com.skillmax.Common.dto.query.course.CourseDto;
import com.skillmax.CourseQueryService.service.CourseQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/query/courses")
public class CourseQueryController {

    private final CourseQueryService courseQueryService;

    public CourseQueryController(CourseQueryService courseQueryService) {
        this.courseQueryService = courseQueryService;
    }

    @GetMapping
    //@Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        return courseQueryService.getAllCourses();
    }


    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable("id") UUID id) {
        return courseQueryService.getCourseById(id);
    }

}
