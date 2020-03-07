package com.unloadbrain.assignment.relay42.metrics;

import org.influxdb.InfluxDB;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfig {

    // The reason behind the mocking is InfluxDB tries to connect with the server.
    @Bean
    @Primary
    public InfluxDB influxDB() {
        return mock(InfluxDB.class);
    }

}
