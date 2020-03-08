package com.unloadbrain.assignment.relay42.metrics.exception;

public class QueryServiceNotFoundException extends RuntimeException {

    public QueryServiceNotFoundException(String message) {
        super(message);
    }
}