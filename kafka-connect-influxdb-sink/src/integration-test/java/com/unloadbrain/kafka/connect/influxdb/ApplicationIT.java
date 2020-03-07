package com.unloadbrain.kafka.connect.influxdb;

import org.influxdb.InfluxDB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationIT {

    @Test
    public void contextLoads() {
    }

    @Configuration
    static class TestConfig {

        @Primary
        @Bean
        public InfluxDB influxDBMock() {
            return mock(InfluxDB.class);
        }
    }
}
