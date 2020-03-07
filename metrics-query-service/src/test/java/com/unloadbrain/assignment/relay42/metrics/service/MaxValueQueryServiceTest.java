package com.unloadbrain.assignment.relay42.metrics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unloadbrain.assignment.relay42.metrics.domain.model.AggregateQueryResult;
import com.unloadbrain.assignment.relay42.metrics.dto.MaxValue;
import com.unloadbrain.assignment.relay42.metrics.dto.Query;
import com.unloadbrain.assignment.relay42.metrics.dto.QueryResponse;
import com.unloadbrain.assignment.relay42.metrics.exception.DataAccessException;
import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MaxValueQueryServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnMaxValue() {

        // Given
        ArgumentCaptor<org.influxdb.dto.Query> influxDbQueryArgumentCaptor
                = ArgumentCaptor.forClass(org.influxdb.dto.Query.class);

        QueryResult queryResultMock = mock(QueryResult.class);
        when(queryResultMock.hasError()).thenReturn(false);

        InfluxDB influxDBMock = mock(InfluxDB.class);
        when(influxDBMock.query(any())).thenReturn(queryResultMock);

        InfluxDBResultMapper resultMapperMock = mock(InfluxDBResultMapper.class);
        when(resultMapperMock.toPOJO(any(QueryResult.class), any(Class.class), anyString()))
                .thenReturn(Collections.singletonList(
                        AggregateQueryResult.builder().time(Instant.now()).result(1.2).build())
                );

        QueryService queryService = new MaxValueQueryService(influxDBMock, resultMapperMock);

        // When
        Query query = Query.builder()
                .operation("max")
                .deviceGroup("deviceGroup1")
                .startTime(1583610967)
                .endTime(1583611000)
                .additionalParams(Collections.singletonMap("aggregateField", "field1"))
                .build();
        QueryResponse<MaxValue> queryResponse = queryService.query(query);

        // Then
        verify(influxDBMock, times(1)).query(influxDbQueryArgumentCaptor.capture());
        assertEquals("SELECT max(field1) AS col1 FROM deviceGroup1 WHERE time >= 1583610967s AND time <= 1583611000s",
                influxDbQueryArgumentCaptor.getValue().getCommand());

        assertTrue(BigDecimal.valueOf(1.2).equals(queryResponse.getResponse().getMax()));
    }

    @Test
    public void shouldReturnZeroWhenNoResultFromInfluxDB() {

        // Given
        ArgumentCaptor<org.influxdb.dto.Query> influxDbQueryArgumentCaptor
                = ArgumentCaptor.forClass(org.influxdb.dto.Query.class);

        QueryResult queryResultMock = mock(QueryResult.class);
        when(queryResultMock.hasError()).thenReturn(false);

        InfluxDB influxDBMock = mock(InfluxDB.class);
        when(influxDBMock.query(any())).thenReturn(queryResultMock);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();

        QueryService queryService = new MaxValueQueryService(influxDBMock, resultMapper);

        // When
        Query query = Query.builder()
                .operation("max")
                .deviceId(Optional.of("deviceId1"))
                .deviceGroup("deviceGroup1")
                .startTime(1583610967)
                .endTime(1583611000)
                .additionalParams(Collections.singletonMap("aggregateField", "field1"))
                .build();
        QueryResponse<MaxValue> queryResponse = queryService.query(query);

        // Then
        verify(influxDBMock, times(1)).query(influxDbQueryArgumentCaptor.capture());
        assertEquals("SELECT max(field1) AS col1 FROM deviceGroup1 WHERE time >= 1583610967s AND time <= 1583611000s AND deviceId = 'Optional[deviceId1]'",
                influxDbQueryArgumentCaptor.getValue().getCommand());

        assertTrue(BigDecimal.ZERO.equals(queryResponse.getResponse().getMax()));
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenMessageCannotBeMappedToBytes() throws JsonProcessingException {

        // Given

        thrown.expect(DataAccessException.class);
        thrown.expectMessage("Could not access influxDB because of Something bad happened to influxDB.");

        QueryResult queryResultMock = mock(QueryResult.class);
        when(queryResultMock.hasError()).thenReturn(true);
        when(queryResultMock.getError()).thenReturn("Something bad happened to influxDB.");

        InfluxDB influxDBMock = mock(InfluxDB.class);
        when(influxDBMock.query(any())).thenReturn(queryResultMock);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();

        QueryService queryService = new MaxValueQueryService(influxDBMock, resultMapper);

        // When
        Query query = Query.builder()
                .operation("max")
                .deviceId(Optional.of("deviceId1"))
                .deviceGroup("deviceGroup1")
                .startTime(1583610967)
                .endTime(1583611000)
                .additionalParams(Collections.singletonMap("aggregateField", "field1"))
                .build();
        queryService.query(query);

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldReturnProperOperationName() {

        // Given
        QueryService queryService = new MaxValueQueryService(mock(InfluxDB.class), mock(InfluxDBResultMapper.class));

        // When
        String operation = queryService.getOperation();

        // Then
        assertEquals("max", operation);
    }
}