package com.skillmax.CourseCommandService.serviceImpl;

import com.skillmax.Common.dto.events.course.*;
import com.skillmax.Common.exception.*;
import com.skillmax.CourseCommandService.kafka.CourseCommandEventProducer;
import com.skillmax.CourseCommandService.model.Course;
import com.skillmax.CourseCommandService.repository.CourseCommandRepository;
import com.skillmax.CourseCommandService.service.CourseCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CourseCommandServiceImpl implements CourseCommandService {

    private final CourseCommandRepository courseCommandRepository;
    private final CourseCommandEventProducer courseCommandEventProducer;

    public CourseCommandServiceImpl(CourseCommandRepository courseCommandRepository,
                                    CourseCommandEventProducer courseCommandEventProducer) {
        this.courseCommandRepository = courseCommandRepository;
        this.courseCommandEventProducer = courseCommandEventProducer;
    }

    @Override
    public Course createCourse(Course course) {
        if (course == null) {
            throw new ValidationException("Course cannot be null");
        }

        try {
            log.info("Creating new course: {}", course);
            Course savedCourse = courseCommandRepository.save(course);
            log.info("Successfully created course with ID: {}", savedCourse.getId());

            CourseCommandCreatedEvent event = new CourseCommandCreatedEvent();

            event.setId(savedCourse.getId());
            event.setTitle(savedCourse.getTitle());
            event.setDescription(savedCourse.getDescription());
            event.setShortDescription(savedCourse.getShortDescription());
            event.setThumbnail(savedCourse.getThumbnail());
            event.setBannerImage(savedCourse.getBannerImage());
            event.setPrice(savedCourse.getPrice());
            event.setOriginalPrice(savedCourse.getOriginalPrice());
            event.setCurrency(savedCourse.getCurrency());
            event.setDuration(savedCourse.getDuration());
            event.setLevel(savedCourse.getLevel());
            event.setLanguage(savedCourse.getLanguage());
            event.setStatus(savedCourse.getStatus());
            event.setIsFeatured(savedCourse.getIsFeatured());
            event.setIsFree(savedCourse.getIsFree());
            event.setMaxStudents(savedCourse.getMaxStudents());
            event.setCurrentStudents(savedCourse.getCurrentStudents());
            event.setRating(savedCourse.getRating());
            event.setTotalRatings(savedCourse.getTotalRatings());
            event.setRequirements(savedCourse.getRequirements());
            event.setLearningOutcomes(savedCourse.getLearningOutcomes());
            event.setTags(savedCourse.getTags());
            event.setCreatedAt(savedCourse.getCreatedAt());
            event.setUpdatedAt(savedCourse.getUpdatedAt());
            event.setPublishedAt(savedCourse.getPublishedAt());

            courseCommandEventProducer.publishCourseCreated(event);

            return savedCourse;
        } catch (Exception e) {
            log.error("Error creating course: {}", e.getMessage(), e);
            throw new BusinessException("Failed to create course", "COURSE_CREATION_FAILED");
        }
    }

    @Override
    public Course updateCourse(String id, Course updatedCourse) {
        UUID courseId;
        try {
            courseId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid UUID format: " + id);
        }

        Course existing = courseCommandRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        updatedCourse.setId(courseId);
        Course saved = courseCommandRepository.save(updatedCourse);

        CourseCommandUpdatedEvent event = new CourseCommandUpdatedEvent();

        event.setId(saved.getId());
        event.setTitle(saved.getTitle());
        event.setDescription(saved.getDescription());
        event.setShortDescription(saved.getShortDescription());
        event.setThumbnail(saved.getThumbnail());
        event.setBannerImage(saved.getBannerImage());
        event.setPrice(saved.getPrice());
        event.setOriginalPrice(saved.getOriginalPrice());
        event.setCurrency(saved.getCurrency());
        event.setDuration(saved.getDuration());
        event.setLevel(saved.getLevel());
        event.setLanguage(saved.getLanguage());
        event.setStatus(saved.getStatus());
        event.setIsFeatured(saved.getIsFeatured());
        event.setIsFree(saved.getIsFree());
        event.setMaxStudents(saved.getMaxStudents());
        event.setCurrentStudents(saved.getCurrentStudents());
        event.setRating(saved.getRating());
        event.setTotalRatings(saved.getTotalRatings());
        event.setRequirements(saved.getRequirements());
        event.setLearningOutcomes(saved.getLearningOutcomes());
        event.setTags(saved.getTags());
        event.setCreatedAt(saved.getCreatedAt());
        event.setUpdatedAt(saved.getUpdatedAt());
        event.setPublishedAt(saved.getPublishedAt());

        courseCommandEventProducer.publishCourseUpdated(event);
        return saved;
    }

    @Override
    public void deleteCourse(UUID id) {
        Course existing = courseCommandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));

        try {
            courseCommandRepository.deleteById(id);
            courseCommandEventProducer.publishedCourseDeleted(new CourseCommandDeletedEvent(id));
        } catch (Exception e) {
            log.error("Failed to delete course with ID {}: {}", id, e.getMessage());
            throw new BusinessException("Failed to delete course", "COURSE_DELETION_FAILED");
        }
    }
}
