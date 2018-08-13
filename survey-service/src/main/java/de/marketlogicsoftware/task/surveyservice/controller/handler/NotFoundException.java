package de.marketlogicsoftware.task.surveyservice.controller.handler;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RequestException {

    private static final long serialVersionUID = -136772411762871977L;

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
