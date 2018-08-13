package de.marketlogicsoftware.task.surveyservice.controller.handler;

import org.springframework.http.HttpStatus;

/**
 * Base exception which is handles in @see {@link AppExceptionHandler}. Thrown when there is a problem in the request
 *
 * @see RuntimeException
 */
public class RequestException extends RuntimeException {

    private static final long serialVersionUID = 6167645146872092698L;

    private HttpStatus errorCode;

    public RequestException(HttpStatus errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}