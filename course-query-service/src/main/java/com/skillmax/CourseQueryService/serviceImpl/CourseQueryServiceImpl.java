package com.skillmax.CourseQueryService.serviceImpl;

import com.skillmax.Common.dto.query.course.CourseDto;
import com.skillmax.Common.exception.ResourceNotFoundException;
import com.skillmax.CourseQueryService.model.Course;
import com.skillmax.CourseQueryService.repository.CourseQueryRepository;
import com.skillmax.CourseQueryService.service.CourseQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseQueryServiceImpl implements CourseQueryService {

    private final CourseQueryRepository courseQueryRepository;

    public CourseQueryServiceImpl(CourseQueryRepository courseQueryRepository) {
        this.courseQueryRepository = courseQueryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        return courseQueryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getCourseById(UUID id) {
        Course course = courseQueryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return convertToDto(course);
    }

    private CourseDto convertToDto(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .version(course.getVersion())
                .title(course.getTitle())
                .description(course.getDescription())
                .shortDescription(course.getShortDescription())
                .instructorId(course.getInstructorId())
                .categoryId(course.getCategoryId())
                .thumbnail(course.getThumbnail())
                .bannerImage(course.getBannerImage())
                .price(course.getPrice())
                .originalPrice(course.getOriginalPrice())
                .currency(course.getCurrency())
                .duration(course.getDuration())
                .level(course.getLevel())
                .language(course.getLanguage())
                .status(course.getStatus())
                .isFeatured(course.getIsFeatured())
                .isFree(course.getIsFree())
                .maxStudents(course.getMaxStudents())
                .currentStudents(course.getCurrentStudents())
                .rating(course.getRating())
                .totalRatings(course.getTotalRatings())
                .requirements(course.getRequirements())
                .learningOutcomes(course.getLearningOutcomes())
                .tags(course.getTags())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .publishedAt(course.getPublishedAt())
                .moduleIds(course.getModuleIds())
                .enrollmentIds(course.getEnrollmentIds())
                .assessmentIds(course.getAssessmentIds())
                .contentIds(course.getContentIds())
                .build();
    }
}
