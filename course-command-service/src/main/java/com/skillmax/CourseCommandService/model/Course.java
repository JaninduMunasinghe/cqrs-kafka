package com.skillmax.CourseCommandService.model;

import com.skillmax.Common.enums.CourseLevel;
import com.skillmax.Common.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(length = 512)
    private String shortDescription;

    // Replacing object relationships with UUIDs
    private UUID instructorId;
    private UUID categoryId;

    private String thumbnail;
    private String bannerImage;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String currency;
    private Integer duration; // minutes

    @Enumerated(EnumType.STRING)
    private CourseLevel level;

    private String language;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    private Boolean isFeatured;
    private Boolean isFree;

    private Integer maxStudents;
    private Integer currentStudents;

    private Double rating;
    private Integer totalRatings;

    @ElementCollection
    private List<String> requirements = new ArrayList<>();

    @ElementCollection
    private List<String> learningOutcomes = new ArrayList<>();

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;

    // Replacing relationships with UUID references
    @ElementCollection
    private List<UUID> moduleIds = new ArrayList<>();

    @ElementCollection
    private List<UUID> enrollmentIds = new ArrayList<>();

    @ElementCollection
    private List<UUID> assessmentIds = new ArrayList<>();

    @ElementCollection
    private List<UUID> contentIds = new ArrayList<>();


}
