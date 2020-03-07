package com.unloadbrain.assignment.relay42.virtualdevices.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides MQTT related beans.
 */
@Slf4j
@Configuration
public class MqttClientConfig {

    private static final MemoryPersistence persistence = new MemoryPersistence();

    private final String mqttBrokerUrl;
    private final String mqttClientId;

    public MqttClientConfig(@Value("${virtualdevices.mqtt.broker.url}") String mqttBrokerUrl,
                            @Value("${virtualdevices.mqtt.clientId}") String mqttClientId) {
        this.mqttBrokerUrl = mqttBrokerUrl;
        this.mqttClientId = mqttClientId;
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {

        MqttClient mqttClient = new MqttClient(mqttBrokerUrl, mqttClientId, persistence);

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        log.debug("Connecting to broker: {}", mqttBrokerUrl);
        mqttClient.connect(connOpts);

        log.info("Connected with broker {}", mqttBrokerUrl);
        return mqttClient;
    }
}
