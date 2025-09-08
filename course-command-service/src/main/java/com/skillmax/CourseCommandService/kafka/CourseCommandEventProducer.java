package com.skillmax.CourseCommandService.kafka;

import com.skillmax.Common.dto.events.course.CourseCommandCreatedEvent;
import com.skillmax.Common.dto.events.course.CourseCommandDeletedEvent;
import com.skillmax.Common.dto.events.course.CourseCommandUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CourseCommandEventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCourseCreated(CourseCommandCreatedEvent event) {
        kafkaTemplate.send("course-command-events", event.getId().toString(), event);
    }

    public void publishCourseUpdated(CourseCommandUpdatedEvent event) {
        kafkaTemplate.send("course-command-events", event.getId().toString(), event);
    }

    public void publishedCourseDeleted(CourseCommandDeletedEvent event) {
        kafkaTemplate.send("course-command-events", event.getId().toString(), event);
    }
}
