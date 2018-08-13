package de.marketlogicsoftware.task.surveyservice.controller.handler;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RequestException {

    private static final long serialVersionUID = 446908283346368837L;

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}