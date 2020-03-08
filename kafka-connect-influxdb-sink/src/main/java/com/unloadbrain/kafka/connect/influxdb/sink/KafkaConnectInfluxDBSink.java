package com.unloadbrain.kafka.connect.influxdb.sink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.unloadbrain.kafka.connect.influxdb.exception.KafkaMessagePayloadConversionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class KafkaConnectInfluxDBSink {

    private final InfluxDB influxDB;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "#{'${kafka.connector.topics}'.split(',')}")
    public void kafkaToInfluxDB(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String payload) {

        String flattenedPayload = JsonFlattener.flatten(payload);

        Map<String, Object> fields = null;
        try {
            fields = mapper.readValue(flattenedPayload, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new KafkaMessagePayloadConversionException("Could not transform json to HashMap.");
        }

        Point point = Point.measurement(topic).fields(fields).build();
        influxDB.write(point);
    }

}
