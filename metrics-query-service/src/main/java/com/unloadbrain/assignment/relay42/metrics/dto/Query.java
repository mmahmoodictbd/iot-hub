package com.unloadbrain.assignment.relay42.metrics.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Builder
public class Query {

    private final String operation;

    @Builder.Default
    private final Optional<String> deviceId = Optional.empty();

    private final String deviceGroup;

    private final long startTime;

    private final long endTime;

    @Builder.Default
    private final Map<String, String> additionalParams = new HashMap<>();
}
