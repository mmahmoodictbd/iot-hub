package com.unloadbrain.assignment.relay42.virtualdevices;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, ContextLoadIT.TestConfig.class})
@ActiveProfiles("it")
public class ContextLoadIT {

    @Test
    public void contextLoads() {
        // Verify application context load properly.
    }

    @Configuration
    static class TestConfig {

        // The reason behind the mocking is actual MQTT client tries to connect with the broker.
        @Primary
        @Bean
        public MqttClient mqttClient() {
            return mock(MqttClient.class);
        }
    }
}