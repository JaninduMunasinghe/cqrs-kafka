package com.skillmax.CourseCommandService.repository;

import com.skillmax.CourseCommandService.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseCommandRepository extends JpaRepository<Course, UUID> {
}
