package com.trustly.github.scrapper.exception.handler;

import java.io.Serializable;

public class Error implements Serializable {

    private static final long serialVersionUID = 578434L;

    private String errorMessage;
    private String exception;

    public Error(String errorMessage, String exception) {
        this.errorMessage = errorMessage;
        this.exception = exception;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getException() {
        return exception;
    }
}
