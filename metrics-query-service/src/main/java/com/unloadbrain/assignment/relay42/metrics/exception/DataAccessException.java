package com.unloadbrain.assignment.relay42.metrics.exception;

/**
 * Exception class in case InfluxDB data could not be accessed.
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }
}