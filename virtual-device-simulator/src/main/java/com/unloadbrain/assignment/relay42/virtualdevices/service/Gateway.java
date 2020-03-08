package com.unloadbrain.assignment.relay42.virtualdevices.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unloadbrain.assignment.relay42.virtualdevices.sensors.Sensor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class Gateway implements Observer {

    public static final int MQTT_MESSAGE_QOS = 2;
    private static Map<String, Sensor> registeredSensors = new ConcurrentHashMap<>();

    private final MqttClient mqttClient;
    private final ObjectMapper objectMapper;

    public void register(Sensor sensor) {
        sensor.addObserver(this);
        registeredSensors.putIfAbsent(sensor.getDeviceUuid(), sensor);
        log.info("Device ID {} is registered successfully.", sensor.getDeviceUuid());
    }

    public void deregister(Sensor sensor) {
        sensor.deleteObserver(this);
        registeredSensors.remove(sensor.getDeviceUuid());
        log.info("Device ID {} is deregistered successfully.", sensor.getDeviceUuid());
    }

    public List<String> getRegisteredSensorsDeviceIdList() {
        return registeredSensors.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public void update(Observable observable, Object deviceData) {

        Sensor sensor = (Sensor) observable;
        log.debug("Received an update from {}", sensor.getDeviceUuid());

        Map<String, Object> message = new HashMap<>();
        message.put("sourceId", "VirtualGateway");
        message.put("messageId", UUID.randomUUID().toString());
        message.put("deviceId", sensor.getDeviceUuid());
        message.put("deviceType", sensor.getType());
        message.put("deviceData", deviceData);

        MqttMessage mqttMessage = null;
        try {
            mqttMessage = new MqttMessage(objectMapper.writeValueAsBytes(message));
        } catch (JsonProcessingException ex) {
            log.error("Could not serialize device message to MqttMessage Bytes. Exception: {}", ex);
            throw new RuntimeException("Could not serialize device message to MqttMessage Bytes.");
        }
        mqttMessage.setQos(MQTT_MESSAGE_QOS);

        try {
            mqttClient.publish(sensor.getType(), mqttMessage);
            log.debug("Published a message {} to topic {}", message, sensor.getType());
        } catch (MqttException ex) {
            log.error("Could not publish MQTT message to the server. Exception: {}", ex);
            throw new RuntimeException("Could not publish MQTT message to the server.");
        }
    }
}
