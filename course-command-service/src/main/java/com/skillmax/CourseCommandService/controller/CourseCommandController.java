package com.skillmax.CourseCommandService.controller;

import com.skillmax.Common.dto.ApiResponse;
import com.skillmax.CourseCommandService.model.Course;
import com.skillmax.CourseCommandService.service.CourseCommandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseCommandController {

    @Autowired
    private CourseCommandService courseCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createCourse(
            @Valid @RequestBody Course course) {
        // throws ValidationException or BusinessException on failure
        courseCommandService.createCourse(course);
        return ResponseEntity
                .ok(ApiResponse.success("Course created successfully"));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateCourse(@Valid @PathVariable("id") String id, @RequestBody Course updatedCourse) {
        courseCommandService.updateCourse(id, updatedCourse);
        return ResponseEntity.ok(ApiResponse.success("Course update successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteCourse(@Valid @PathVariable("id") UUID id) {
        courseCommandService.deleteCourse(id);
        return ResponseEntity.ok(ApiResponse.success("Course Deleted Successfully"));
    }

}
