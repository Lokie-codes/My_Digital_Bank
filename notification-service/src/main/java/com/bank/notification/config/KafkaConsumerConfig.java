package com.bank.notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    // Rely on Spring Boot auto-configuration
}
