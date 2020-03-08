package com.unloadbrain.assignment.relay42.metrics.exception.handler;

import com.unloadbrain.assignment.relay42.metrics.exception.QueryServiceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handle application specific exception in user-friendly way.
 */
@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends BaseExceptionHandler {

    public ApplicationExceptionHandler() {
        super(log);
    }

    @ExceptionHandler(QueryServiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse queryServiceNotFoundException(final QueryServiceNotFoundException ex) {
        log.error("Exception: {} ", ex);
        return new ErrorResponse("QUERY_SERVICE_NOT_FOUND", "No query service found for specified operation");
    }
}