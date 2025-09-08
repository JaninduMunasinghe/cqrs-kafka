package com.skillmax.CourseQueryService.consumer;

import com.skillmax.Common.dto.events.course.CourseCommandCreatedEvent;
import com.skillmax.CourseQueryService.model.Course;
import com.skillmax.CourseQueryService.repository.CourseQueryRepository;
import com.skillmax.Common.dto.events.course.CourseCommandDeletedEvent;
import com.skillmax.Common.dto.events.course.CourseCommandUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@KafkaListener(topics = "course-command-events", groupId = "course-query-group")
@Service
@Slf4j
@RequiredArgsConstructor
public class CourseCommandEventConsumer {

    private final CourseQueryRepository courseRepository;

    @KafkaHandler
    @Transactional
    @Retryable(
            value = {OptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    public void consume(CourseCommandCreatedEvent event) {
        log.info("Received course creation event: {}", event.getId());

        try {
            Course course;
            boolean isNewCourse = !courseRepository.existsById(event.getId());

            if (isNewCourse) {
                course = new Course();
                course.setVersion(0L); // Initialize version for new entities
            } else {
                course = courseRepository.findById(event.getId())
                        .orElseThrow(() -> new RuntimeException("Course not found: " + event.getId()));
            }

            createCourseFromEvent(course, event);

            if (isNewCourse) {
                courseRepository.save(course);
            } else {
                courseRepository.saveAndFlush(course);
            }

            log.info("Successfully processed course creation event for course ID: {}", event.getId());

        } catch (OptimisticLockingFailureException e) {
            log.warn("Optimistic locking failure for course ID: {}. Retrying...", event.getId());
            throw e;
        } catch (Exception e) {
            log.error("Error processing course creation event for course ID: {}", event.getId(), e);
            throw new RuntimeException("Failed to process course creation event", e);
        }
    }

    @KafkaHandler
    @Transactional
    @Retryable(
            value = {OptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    public void consume(CourseCommandUpdatedEvent event) {
        log.info("Received course update event: {}", event.getId());

        try {
            Course course = courseRepository.findById(event.getId())
                    .orElseThrow(() -> new RuntimeException("Course not found for update: " + event.getId()));

            updateCourseFromEvent(course, event);
            courseRepository.saveAndFlush(course);

            log.info("Successfully processed course update event for course ID: {}", event.getId());

        } catch (OptimisticLockingFailureException e) {
            log.warn("Optimistic locking failure for course update ID: {}. Retrying...", event.getId());
            throw e;
        } catch (Exception e) {
            log.error("Error processing course update event for course ID: {}", event.getId(), e);
            throw new RuntimeException("Failed to process course update event", e);
        }
    }

    @Transactional
    @KafkaHandler(isDefault = true)
    public void consume(CourseCommandDeletedEvent event) {
        log.info("Received course deletion event: {}", event.getId());

        try {
            if (!courseRepository.existsById(event.getId())) {
                log.warn("Course with ID {} not found for deletion", event.getId());
                return;
            }

            courseRepository.deleteById(event.getId());
            log.info("Successfully processed course deletion event for course ID: {}", event.getId());

        } catch (Exception e) {
            log.error("Error processing course deletion event for course ID: {}", event.getId(), e);
            throw new RuntimeException("Failed to process course deletion event", e);
        }
    }


    private void createCourseFromEvent(Course course, CourseCommandCreatedEvent event) {
        course.setId(event.getId());
        course.setTitle(event.getTitle());
        course.setDescription(event.getDescription());
        course.setShortDescription(event.getShortDescription());
        course.setInstructorId(event.getInstructorId());
        course.setCategoryId(event.getCategoryId());
        course.setThumbnail(event.getThumbnail());
        course.setBannerImage(event.getBannerImage());
        course.setPrice(event.getPrice());
        course.setOriginalPrice(event.getOriginalPrice());
        course.setCurrency(event.getCurrency());
        course.setDuration(event.getDuration());
        course.setLanguage(event.getLanguage());
        course.setIsFeatured(event.getIsFeatured());
        course.setIsFree(event.getIsFree());
        course.setMaxStudents(event.getMaxStudents());
        course.setCurrentStudents(event.getCurrentStudents());
        course.setRating(event.getRating());
        course.setTotalRatings(event.getTotalRatings());
        course.setRequirements(event.getRequirements());
        course.setLearningOutcomes(event.getLearningOutcomes());
        course.setTags(event.getTags());
        course.setCreatedAt(event.getCreatedAt());
        course.setUpdatedAt(event.getUpdatedAt());
        course.setPublishedAt(event.getPublishedAt());
        course.setModuleIds(event.getModuleIds());
        course.setEnrollmentIds(event.getEnrollmentIds());
        course.setAssessmentIds(event.getAssessmentIds());
        course.setContentIds(event.getContentIds());
        course.setLevel(event.getLevel());
        course.setStatus(event.getStatus());

    }

    private void updateCourseFromEvent(Course course, CourseCommandUpdatedEvent event) {
        course.setTitle(event.getTitle());
        course.setDescription(event.getDescription());
        course.setShortDescription(event.getShortDescription());
        course.setInstructorId(event.getInstructorId());
        course.setCategoryId(event.getCategoryId());
        course.setThumbnail(event.getThumbnail());
        course.setBannerImage(event.getBannerImage());
        course.setPrice(event.getPrice());
        course.setOriginalPrice(event.getOriginalPrice());
        course.setCurrency(event.getCurrency());
        course.setDuration(event.getDuration());
        course.setLanguage(event.getLanguage());
        course.setIsFeatured(event.getIsFeatured());
        course.setIsFree(event.getIsFree());
        course.setMaxStudents(event.getMaxStudents());
        course.setCurrentStudents(event.getCurrentStudents());
        course.setRating(event.getRating());
        course.setTotalRatings(event.getTotalRatings());
        course.setRequirements(event.getRequirements());
        course.setLearningOutcomes(event.getLearningOutcomes());
        course.setTags(event.getTags());
        course.setCreatedAt(event.getCreatedAt());
        course.setUpdatedAt(event.getUpdatedAt());
        course.setPublishedAt(event.getPublishedAt());
        course.setModuleIds(event.getModuleIds());
        course.setEnrollmentIds(event.getEnrollmentIds());
        course.setAssessmentIds(event.getAssessmentIds());
        course.setContentIds(event.getContentIds());
        course.setLevel(event.getLevel());
        course.setStatus(event.getStatus());
    }
}
