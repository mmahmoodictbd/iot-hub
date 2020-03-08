package com.unloadbrain.assignment.relay42.metrics.service;

import com.unloadbrain.assignment.relay42.metrics.dto.Query;
import com.unloadbrain.assignment.relay42.metrics.dto.QueryResponse;
import com.unloadbrain.assignment.relay42.metrics.exception.QueryServiceNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QueryServiceFactoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnQueryServiceBasedOnOperationType() {

        // Given

        QueryService newOpQueryService = new QueryService() {
            @Override
            public String getOperation() {
                return "newOp";
            }

            @Override
            public QueryResponse query(Query query) {
                return QueryResponse.<Map<String, String>>builder()
                        .query(query)
                        .response(Collections.singletonMap("key", "value"))
                        .build();
            }
        };

        QueryServiceFactory queryServiceFactory = new QueryServiceFactory(Collections.singletonList(newOpQueryService));

        // When
        QueryService queryService = queryServiceFactory.getService("newOp");

        // Then
        assertEquals("newOp", queryService.getOperation());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenNoOperationMappedWithQueryService() {

        // Given

        thrown.expect(QueryServiceNotFoundException.class);
        thrown.expectMessage("Unknown operation: newOp");

        QueryServiceFactory queryServiceFactory = new QueryServiceFactory();

        // When
        queryServiceFactory.getService("newOp");

        // Then
        // Expect test to be passed.
    }

}