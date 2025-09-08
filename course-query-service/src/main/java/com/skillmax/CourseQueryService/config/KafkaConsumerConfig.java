package com.skillmax.CourseQueryService.config;

import com.skillmax.Common.dto.events.course.CourseCommandCreatedEvent;
import com.skillmax.Common.dto.events.course.CourseCommandDeletedEvent;
import com.skillmax.Common.dto.events.course.CourseCommandUpdatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "40.81.24.195:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "course-query-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new JsonDeserializer<>(Object.class)
                        .trustedPackages("com.skillmax.Common.dto.events.course")
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public DefaultJackson2JavaTypeMapper typeMapper() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("created", CourseCommandCreatedEvent.class);
        mappings.put("updated", CourseCommandUpdatedEvent.class);
        mappings.put("deleted", CourseCommandDeletedEvent.class);
        typeMapper.setIdClassMapping(mappings);
        typeMapper.addTrustedPackages("com.skillmax.Common.dto.events.course");
        return typeMapper;
    }

}

