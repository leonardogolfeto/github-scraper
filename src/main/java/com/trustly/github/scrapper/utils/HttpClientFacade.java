package com.trustly.github.scrapper.utils;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

import javax.inject.Singleton;

import com.trustly.github.scrapper.exception.ScrapperBadRequestException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Singleton
public class HttpClientFacade {

    private final HttpClient client;

    public HttpClientFacade() {
        this.client = HttpClient.newBuilder().build();;
    }

    public String sendGetTo(URI address) {
        try {
            return client.send(createRequestBy(address), ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new ScrapperBadRequestException(e);
        }
    }

    private HttpRequest createRequestBy(URI address) {
        return HttpRequest.newBuilder(address).GET().build();
    }

}
