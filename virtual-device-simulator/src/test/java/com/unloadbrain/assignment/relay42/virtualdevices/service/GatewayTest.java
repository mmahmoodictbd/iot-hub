package com.unloadbrain.assignment.relay42.virtualdevices.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unloadbrain.assignment.relay42.virtualdevices.sensors.Sensor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GatewayTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldPublishMqttMessageOnRegisteredDeviceDataEmit() throws MqttException, JsonProcessingException {

        // Given
        ArgumentCaptor<String> mqttMessageTopicArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MqttMessage> mqttMessageArgumentCaptor = ArgumentCaptor.forClass(MqttMessage.class);

        MqttClient mqttClientMock = mock(MqttClient.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Gateway gateway = new Gateway(mqttClientMock, objectMapper);

        Sensor<Map<String, String>> sensor = new Sensor<>(gateway, "sensor1") {
            {
                pairing();
            }

            @Override
            public String getType() {
                return "testSensor";
            }
        };

        // When
        sensor.publishChange(Collections.singletonMap("reading", "1.2"));

        // Then
        verify(mqttClientMock, times(1))
                .publish(mqttMessageTopicArgumentCaptor.capture(), mqttMessageArgumentCaptor.capture());
        assertEquals("testSensor", mqttMessageTopicArgumentCaptor.getValue());

        assertEquals(2, mqttMessageArgumentCaptor.getValue().getQos());
        JsonNode captured = objectMapper.readTree(new String(mqttMessageArgumentCaptor.getValue().getPayload()));
        assertEquals("VirtualGateway", captured.get("sourceId").asText());
        assertTrue(captured.get("messageId").asText().matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"));
        assertEquals("testSensor", captured.get("deviceType").asText());
        assertEquals("sensor1", captured.get("deviceId").asText());
        assertEquals("1.2", captured.get("deviceData").get("reading").asText());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenMessageCannotBeMappedToBytes() throws JsonProcessingException {

        // Given

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Could not serialize device message to MqttMessage Bytes.");

        ObjectMapper mapperMock = mock(ObjectMapper.class);
        doThrow(mock(JsonProcessingException.class)).when(mapperMock).writeValueAsBytes(any(HashMap.class));

        MqttClient mqttClientMock = mock(MqttClient.class);
        Gateway gateway = new Gateway(mqttClientMock, mapperMock);

        Sensor<Map<String, String>> sensor = new Sensor<>(gateway, "sensor1") {
            {
                pairing();
            }

            @Override
            public String getType() {
                return "testSensor";
            }
        };

        // When
        sensor.publishChange(Collections.singletonMap("reading", "1.2"));

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenMQTTMessageCouldNotBePublished() throws MqttException {

        // Given

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Could not publish MQTT message to the server.");

        MqttClient mqttClientMock = mock(MqttClient.class);
        doThrow(mock(MqttException.class)).when(mqttClientMock).publish(anyString(), any(MqttMessage.class));
        Gateway gateway = new Gateway(mqttClientMock, new ObjectMapper());

        Sensor<Map<String, String>> sensor = new Sensor<>(gateway, "sensor1") {
            {
                pairing();
            }

            @Override
            public String getType() {
                return "testSensor";
            }
        };

        // When
        sensor.publishChange(Collections.singletonMap("reading", "1.2"));

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldDeregisterSensor() throws MqttException, JsonProcessingException {

        // Given
        MqttClient mqttClientMock = mock(MqttClient.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Gateway gateway = new Gateway(mqttClientMock, objectMapper);

        Sensor<Map<String, String>> sensor = new Sensor<>(gateway, "sensor1") {
            {
                pairing();
            }

            @Override
            public String getType() {
                return "testSensor";
            }
        };

        // When
        sensor.publishChange(Collections.singletonMap("reading", "1.2"));
        sensor.unpairing();

        // Then
        assertTrue(gateway.getRegisteredSensorsDeviceIdList().isEmpty());
    }
}