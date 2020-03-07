package com.unloadbrain.kafka.connect.influxdb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unloadbrain.kafka.connect.influxdb.sink.KafkaConnectInfluxDBSink;
import lombok.AllArgsConstructor;
import org.influxdb.InfluxDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class KafkaConnectInfluxDBSinkConfig {

    private final InfluxDB influxDB;
    private final ObjectMapper objectMapper;

    @Bean
    public KafkaConnectInfluxDBSink kafkaConnectInfluxDBSink() {
        return new KafkaConnectInfluxDBSink(influxDB, objectMapper);
    }

}