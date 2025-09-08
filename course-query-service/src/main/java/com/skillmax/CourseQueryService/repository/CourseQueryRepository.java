package com.skillmax.CourseQueryService.repository;

import com.skillmax.CourseQueryService.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseQueryRepository extends JpaRepository<Course, UUID> {
}
