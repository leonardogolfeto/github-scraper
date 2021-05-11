package com.trustly.github.scrapper.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.trustly.github.scrapper.exception.ScrapperBadRequestException;

@Provider
public class ScrapperBadRequestExceptionHandler implements ExceptionMapper<ScrapperBadRequestException> {

    @Override
    public Response toResponse(ScrapperBadRequestException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new Error(exception.getMessage(), exception.getClass().getCanonicalName()))
                .build();
    }
}
