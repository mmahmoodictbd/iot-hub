package com.unloadbrain.assignment.relay42.metrics.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryResponse<T> {

    private final Query query;

    private final T response;
}
