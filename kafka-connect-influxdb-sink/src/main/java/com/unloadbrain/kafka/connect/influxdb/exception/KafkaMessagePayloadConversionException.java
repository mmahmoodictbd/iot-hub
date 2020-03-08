package com.unloadbrain.kafka.connect.influxdb.exception;

public class KafkaMessagePayloadConversionException extends RuntimeException {

    public KafkaMessagePayloadConversionException(String message) {
        super(message);
    }
}