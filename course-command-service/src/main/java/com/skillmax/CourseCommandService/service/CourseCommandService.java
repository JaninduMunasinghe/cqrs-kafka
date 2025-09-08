package com.skillmax.CourseCommandService.service;

import com.skillmax.CourseCommandService.model.Course;

import java.util.UUID;

public interface CourseCommandService {

    Course createCourse(Course course);

    Course updateCourse(String id, Course updatedCourse);

    void deleteCourse(UUID id);
}
