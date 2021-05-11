package com.trustly.github.scrapper.utils;

import static com.trustly.github.scrapper.utils.TestConstants.GITHUB_DOMAIN;
import static com.trustly.github.scrapper.utils.TestConstants.INVALID_DOMAIN;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.trustly.github.scrapper.exception.ScrapperBadRequestException;

import java.net.URI;

class HttpClientFacadeTest {

    private HttpClientFacade subject;

    @BeforeEach
    void setUp(){
        subject = new HttpClientFacade();
    }

    @Test
    void shouldSendGetToGithubWithSuccess() {

        String result = subject.sendGetTo(URI.create(GITHUB_DOMAIN));
        assertThat(result).isNotEmpty();
    }

    @Test
    void shouldSendGetToWithError(){
        Assertions.assertThrows(ScrapperBadRequestException.class,
                                () -> subject.sendGetTo(URI.create(INVALID_DOMAIN)));
    }


}