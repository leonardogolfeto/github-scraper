package com.trustly.github.scrapper.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegexFacadeTest {

    private RegexFacade subject;

    private final String testResource =
            "<div role=\"row\" >\n" +
            "<div role=\"gridcell\">\n" +
            "</div>\n" +
            "<div role=\"rowheader\">TESTE\n" +
            "</div>\n" +
            "<div role=\"gridcell\">\n" +
            "</div>\n" +
            "<div role=\"gridcell\">\n" +
            "</div>\n" +
            "</div>\n" +
            "<div role=\"row\" >\n" +
            "<div role=\"gridcell\">\n" +
            "</div>\n" +
            "<div role=\"rowheader\">\n" +
            "</div>\n" +
            "<div role=\"gridcell\">\n" +
            "</div>\n" +
            "<div role=\"gridcell\">\n" +
            "</div>\n" +
            "</div>";

    @BeforeEach
    void setUp() {
        subject = new RegexFacade();
    }

    @Test
    void shouldFindSingleResult() {
        assertThat(subject.findSingleResult(testResource, RegexFacade.ROW_HEADER_PATTERN).trim())
                .isEqualTo("<div role=\"rowheader\">TESTE\n</div>".trim());
    }

    @Test
    void shouldNotFindSingleResult() {
        assertThat(subject.findSingleResult(testResource, "<strong>"))
                .isNull();
    }

    @Test
    void shouldFindMultiplesResultsWithMultiplesRows() {
        assertThat(subject.findMultiplesResults(testResource, RegexFacade.ROW_PATTERN))
                .hasSize(2).anyMatch(result -> result.equals("<div role=\"row\" >\n" +
                "<div role=\"gridcell\">\n" +
                "</div>\n" +
                "<div role=\"rowheader\">TESTE\n" +
                "</div>\n" +
                "<div role=\"gridcell\">\n" +
                "</div>\n" +
                "<div role=\"gridcell\">\n" +
                "</div>"));
    }

    @Test
    void shouldFindTextBetweenTwoDivs() {
        assertThat(subject.findTextBetween("<div role=\"rowheader\">TESTE</div>", ">", "<"))
                .isEqualTo("TESTE");
    }
}