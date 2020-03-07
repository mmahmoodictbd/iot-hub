package com.unloadbrain.assignment.relay42.metrics.service;

import com.unloadbrain.assignment.relay42.metrics.dto.Query;
import com.unloadbrain.assignment.relay42.metrics.dto.QueryResponse;

public interface QueryService<T> {

    String getOperation();

    QueryResponse<T> query(Query query);
}
