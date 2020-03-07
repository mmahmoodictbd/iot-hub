package com.unloadbrain.kafka.connect.influxdb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unloadbrain.kafka.connect.influxdb.prop.KafkaConnectInfluxDBSinkProperties;
import com.unloadbrain.kafka.connect.influxdb.prop.KafkaConnectInfluxDBSinkPropertiesReader;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@AllArgsConstructor
@Configuration
public class AppConfig {

    private final Environment environment;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public KafkaConnectInfluxDBSinkProperties kafkaConnectInfluxDBSinkProperties() {
        return new KafkaConnectInfluxDBSinkPropertiesReader(environment).read();
    }

}