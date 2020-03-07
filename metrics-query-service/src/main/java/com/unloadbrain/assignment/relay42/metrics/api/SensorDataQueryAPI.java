package com.unloadbrain.assignment.relay42.metrics.api;

import com.unloadbrain.assignment.relay42.metrics.dto.Query;
import com.unloadbrain.assignment.relay42.metrics.dto.QueryResponse;
import com.unloadbrain.assignment.relay42.metrics.service.QueryServiceFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;


/**
 * This class provide API endpoints to sensor data.
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/devices/metrics")
public class SensorDataQueryAPI {

    private final QueryServiceFactory factory;

    /**
     * Return sensor data with start and end time limit.
     *
     * @param startTime the start time in unix timestamp.
     * @param endTime   the end time unix timestamp.
     * @return the sensor data response DTO.
     */
    @PostMapping("/aggregate")
    public ResponseEntity<QueryResponse> query(
            @RequestParam String aggregateOperation,
            @RequestParam String aggregateField,
            @RequestParam String deviceGroup,
            @RequestParam long startTime,
            @RequestParam long endTime,
            @RequestParam(required = false) String deviceId) {

        Query query = Query.builder()
                .operation(aggregateOperation)
                .deviceId(Optional.ofNullable(deviceId))
                .deviceGroup(deviceGroup)
                .startTime(startTime)
                .endTime(endTime)
                .additionalParams(Collections.singletonMap("aggregateField", aggregateField))
                .build();

        return new ResponseEntity<>(factory.getService(aggregateOperation).query(query), HttpStatus.OK);
    }
}