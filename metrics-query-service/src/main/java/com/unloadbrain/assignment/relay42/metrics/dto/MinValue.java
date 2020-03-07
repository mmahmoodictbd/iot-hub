package com.unloadbrain.assignment.relay42.metrics.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MinValue {

    private final BigDecimal min;
}
