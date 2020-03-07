package com.unloadbrain.assignment.relay42.virtualdevices.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unloadbrain.assignment.relay42.virtualdevices.sensors.heartrate.VirtualHeartRateMonitor;
import com.unloadbrain.assignment.relay42.virtualdevices.sensors.temperature.VirtualTemperatureSensor;
import com.unloadbrain.assignment.relay42.virtualdevices.service.Gateway;
import com.unloadbrain.assignment.relay42.virtualdevices.service.RandomDataStreamEmitter;
import com.unloadbrain.assignment.relay42.virtualdevices.sensors.fuelgauge.VirtualFuelGaugeSensor;
import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Provides application related beans.
 */
@Configuration
@AllArgsConstructor
public class AppConfig {

    private final MqttClient mqttClient;

    @PostConstruct
    public void powerUpVirtualDevices() {
        RandomDataStreamEmitter randomDataStreamEmitter = new RandomDataStreamEmitter();
        new VirtualTemperatureSensor(gateway(), randomDataStreamEmitter);
        new VirtualHeartRateMonitor(gateway(), randomDataStreamEmitter);
        new VirtualFuelGaugeSensor(gateway(), randomDataStreamEmitter);
    }

    @Bean
    public Gateway gateway() {
        return new Gateway(mqttClient, objectMapper());
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }

}
