package de.marketlogicsoftware.task.surveyservice.controller.handler;

import java.io.Serializable;

/**
 * holds data for error response
 */
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 2854102527276171578L;


    private int errorCode;

    private String error_description;

    /** used during deserialization when tests are running */
    public ErrorResponse() {
    }

    /**
     * assigns give parameters to corresponding properties
     *
     * @param errorCode
     * @param errorMessage
     */
    public ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.error_description = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
