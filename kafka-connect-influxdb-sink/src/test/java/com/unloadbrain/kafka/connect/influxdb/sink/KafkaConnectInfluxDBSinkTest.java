package com.unloadbrain.kafka.connect.influxdb.sink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unloadbrain.kafka.connect.influxdb.exception.KafkaMessagePayloadConversionException;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class KafkaConnectInfluxDBSinkTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFlattenThePayloadJsonAndCreateInfluxDBPoint() throws JsonProcessingException {

        // Given

        ArgumentCaptor<String> mapperArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Point> influxDbArgumentCaptor = ArgumentCaptor.forClass(Point.class);

        ObjectMapper mapperMock = mock(ObjectMapper.class);
        when(mapperMock.readValue(any(String.class), any(Class.class))).thenReturn(Collections.singletonMap("parent.key", "value"));

        InfluxDB influxDBMock = mock(InfluxDB.class);
        KafkaConnectInfluxDBSink sink = new KafkaConnectInfluxDBSink(influxDBMock, mapperMock);

        // When
        sink.kafkaToInfluxDB("sampleTopic", "{ \"parent\": { \"key\": \"value\" } }");

        // Then
        verify(mapperMock, times(1)).readValue(mapperArgumentCaptor.capture(), any(Class.class));
        assertEquals("{\"parent.key\":\"value\"}", mapperArgumentCaptor.getValue());

        verify(influxDBMock, times(1)).write(influxDbArgumentCaptor.capture());
        Point expectedPoint = Point.measurement("sampleTopic")
                .fields(Collections.singletonMap("parent.key", "value"))
                .build();
        assertEquals(expectedPoint, influxDbArgumentCaptor.getValue());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenPayloadCannotBeMappedToHashMap() throws JsonProcessingException {

        // Given

        thrown.expect(KafkaMessagePayloadConversionException.class);
        thrown.expectMessage("Could not transform json to HashMap.");

        ObjectMapper mapperMock = mock(ObjectMapper.class);
        doThrow(mock(JsonProcessingException.class)).when(mapperMock).readValue(any(String.class), any(Class.class));

        InfluxDB influxDBMock = mock(InfluxDB.class);
        KafkaConnectInfluxDBSink sink = new KafkaConnectInfluxDBSink(influxDBMock, mapperMock);

        // When
        sink.kafkaToInfluxDB("sampleTopic", "{ \"parent\": { \"key\": \"value\" } }");

        // Then
        // Expect test to be passed.
    }

}