package com.unloadbrain.assignment.relay42.metrics.service;

import com.unloadbrain.assignment.relay42.metrics.dto.MinValue;
import com.unloadbrain.assignment.relay42.metrics.dto.Query;
import com.unloadbrain.assignment.relay42.metrics.dto.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class MinValueQueryService implements QueryService<MinValue> {

    @Override
    public String getOperation() {
        return "min";
    }

    @Override
    public QueryResponse<MinValue> query(Query query) {

        // FIXME: Going to be similar to MaxValueQueryService.
        return QueryResponse.<MinValue>builder()
                .query(query)
                .response(MinValue.builder().min(BigDecimal.valueOf(10)).build())
                .build();
    }
}
