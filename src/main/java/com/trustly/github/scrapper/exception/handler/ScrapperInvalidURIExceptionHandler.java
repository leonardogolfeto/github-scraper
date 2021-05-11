package com.trustly.github.scrapper.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.trustly.github.scrapper.exception.ScrapperBadRequestException;
import com.trustly.github.scrapper.exception.ScrapperInvalidURIException;

@Provider
public class ScrapperInvalidURIExceptionHandler implements ExceptionMapper<ScrapperInvalidURIException> {

    @Override
    public Response toResponse(ScrapperInvalidURIException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new Error(exception.getMessage(), exception.getClass().getCanonicalName()))
                .build();
    }
}
