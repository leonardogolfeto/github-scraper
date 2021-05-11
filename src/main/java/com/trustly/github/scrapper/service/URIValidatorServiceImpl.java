package com.trustly.github.scrapper.service;

import javax.inject.Singleton;

import com.trustly.github.scrapper.exception.ScrapperInvalidURIException;

import java.net.URI;

@Singleton
public class URIValidatorServiceImpl implements URIValidatorService {

    public static final String MALFORMED_URI = "Malformed URI: %s";
    public static final String GITHUB = "github.com";
    public static final String OUT_OF_DOMAIN = "URI outside the github domain: %s";

    @Override
    public URI validate(String uri) {

        URI result = convertStringToURI(uri);
        verifyDomain(result);

        return result;
    }

    private URI convertStringToURI(String uri) {
        URI result;
        try {
            result = URI.create(uri);
        } catch (Exception ex) {
            throw new ScrapperInvalidURIException(String.format(MALFORMED_URI, uri));
        }
        return result;
    }

    private void verifyDomain(URI uri) {
        if(!uri.getHost().equals(GITHUB)){
            throw new ScrapperInvalidURIException(String.format(OUT_OF_DOMAIN, uri.toString()));
        }
    }
}
