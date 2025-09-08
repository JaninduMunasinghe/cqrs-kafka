package com.skillmax.CourseQueryService.model;


import com.skillmax.Common.enums.CourseLevel;
import com.skillmax.Common.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
public class Course {

    @Id
    private UUID id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L; //for optimistic locking

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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> requirements = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> learningOutcomes = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;

    // Replacing relationships with UUID references
    @ElementCollection(fetch = FetchType.EAGER)
    private List<UUID> moduleIds = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<UUID> enrollmentIds = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<UUID> assessmentIds = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<UUID> contentIds = new ArrayList<>();

}
