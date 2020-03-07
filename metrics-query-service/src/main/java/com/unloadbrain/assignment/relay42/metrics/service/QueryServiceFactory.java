package com.unloadbrain.assignment.relay42.metrics.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class QueryServiceFactory {

    private final Map<String, QueryService> queryServiceCache = new ConcurrentHashMap<>();

    @Autowired
    public QueryServiceFactory(List<QueryService> queryServices) {
        queryServices.forEach(queryService ->
                queryServiceCache.put(queryService.getOperation(), queryService)
        );
    }

    public QueryService getService(String operation) {

        QueryService service = queryServiceCache.get(operation);
        if (service == null) {
            throw new RuntimeException(String.format("Unknown operation: %s", operation));
        }
        return service;
    }
}
