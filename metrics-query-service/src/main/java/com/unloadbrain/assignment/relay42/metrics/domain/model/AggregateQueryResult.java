package com.unloadbrain.assignment.relay42.metrics.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.influxdb.annotation.Column;

import java.time.Instant;

/**
 * Domain model class to read persisted data from InfluxDB.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AggregateQueryResult {

    @Column(name = "time")
    private Instant time;

    @Column(name = "col1")
    private double result;
}