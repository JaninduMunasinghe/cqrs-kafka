package com.skillmax.CourseQueryService.service;

import com.skillmax.Common.dto.query.course.CourseDto;

import java.util.List;
import java.util.UUID;

public interface CourseQueryService {

    List<CourseDto> getAllCourses();
    CourseDto getCourseById(UUID id);
}
