package de.marketlogicsoftware.task.surveyservice.controller.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles application exceptions which are thrown and not cought
 *
 * @see ControllerAdvice
 */
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * this exception is handles which is thrown manually
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorResponse> handleResponseErrorException(Exception ex) {
        RequestException e = (RequestException) ex;
        LOGGER.info("the code thrown exception with code = {} message = {}", e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().value(), e.getMessage()), e.getErrorCode());
    }
}