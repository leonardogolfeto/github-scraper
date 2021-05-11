package com.trustly.github.scrapper.service;

import static com.trustly.github.scrapper.utils.TestConstants.GITHUB_DOMAIN;
import static com.trustly.github.scrapper.utils.TestConstants.INVALID_DOMAIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.trustly.github.scrapper.exception.ScrapperInvalidURIException;

import java.net.URI;

class URIValidatorServiceImplTest {

    private URIValidatorServiceImpl subject;

    @BeforeEach
    void setUp(){
        subject = new URIValidatorServiceImpl();
    }

    @Test
    void shouldValidateURIWithSuccess() {

        assertThat(subject.validate(GITHUB_DOMAIN))
                .isEqualTo(URI.create(GITHUB_DOMAIN));
    }

    @Test
    void shouldTryValidateURIWhenURIIsOutsideOfGithubDomain() {
        assertThrows(ScrapperInvalidURIException.class,
                () -> subject.validate(INVALID_DOMAIN));
    }

    @Test
    void shouldTryValidateURIWhenURIIsInvalid() {
        assertThrows(ScrapperInvalidURIException.class,
                () -> subject.validate(null));
    }
}