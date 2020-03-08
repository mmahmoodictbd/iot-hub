package com.unloadbrain.assignment.relay42.metrics.service;

import com.unloadbrain.assignment.relay42.metrics.domain.model.AggregateQueryResult;
import com.unloadbrain.assignment.relay42.metrics.dto.MaxValue;
import com.unloadbrain.assignment.relay42.metrics.dto.Query;
import com.unloadbrain.assignment.relay42.metrics.dto.QueryResponse;
import com.unloadbrain.assignment.relay42.metrics.exception.DataAccessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MaxValueQueryService implements QueryService<MaxValue> {

    private final InfluxDB influxDB;
    private final InfluxDBResultMapper resultMapper;

    @Override
    public String getOperation() {
        return "max";
    }

    @Override
    public QueryResponse<MaxValue> query(Query query) {

        // Note: InfluxDB query is SQL Injection safe.
        String queryStatement;
        if (query.getDeviceId().isPresent()) {
            queryStatement = String.format("SELECT max(%s) AS col1 FROM %s WHERE time >= %ss AND time <= %ss AND deviceId = '%s'",
                    query.getAdditionalParams().get("aggregateField"),
                    query.getDeviceGroup(), query.getStartTime(), query.getEndTime(), query.getDeviceId()
            );
        } else {
            queryStatement = String.format("SELECT max(%s) AS col1 FROM %s WHERE time >= %ss AND time <= %ss",
                    query.getAdditionalParams().get("aggregateField"),
                    query.getDeviceGroup(), query.getStartTime(), query.getEndTime()
            );
        }

        QueryResult queryResult = influxDB.query(new org.influxdb.dto.Query(queryStatement));
        log.debug("QueryResult: {}", queryResult);
        if (queryResult.hasError()) {
            throw new DataAccessException("Could not access influxDB because of " + queryResult.getError());
        }

        List<AggregateQueryResult> resultList = resultMapper.toPOJO(queryResult, AggregateQueryResult.class, query.getDeviceGroup());

        if (resultList != null && !resultList.isEmpty()) {
            return QueryResponse.<MaxValue>builder()
                    .query(query)
                    .response(MaxValue.builder().max(BigDecimal.valueOf(resultList.get(0).getResult())).build())
                    .build();
        } else {
            return QueryResponse.<MaxValue>builder()
                    .query(query)
                    .response(MaxValue.builder().max(BigDecimal.ZERO).build())
                    .build();
        }
    }

}
