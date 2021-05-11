package com.trustly.github.scrapper.service;

import static com.trustly.github.scrapper.utils.TestConstants.GITHUB_DOMAIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.trustly.github.scrapper.domain.Directory;
import com.trustly.github.scrapper.domain.FilesGroupedByExtension;

import java.net.URI;
import java.util.Collections;
import java.util.List;

class ScrapeServiceImplTest {

    @InjectMocks
    private ScrapeServiceImpl subject;

    @Mock
    private URIValidatorService uriValidatorService;

    @Mock
    private DirectoryEnumeratorService directoryEnumeratorService;

    @Mock
    private GrouperService grouperService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldScrapeGithubRepositoryWithSuccess() {

        URI githubURI = URI.create(GITHUB_DOMAIN);
        Directory mockedDirectory = mockDirectory(githubURI);

        when(uriValidatorService.validate(eq(GITHUB_DOMAIN))).thenReturn(githubURI);
        when(directoryEnumeratorService.enumerate(eq(githubURI))).thenReturn(mockedDirectory);
        when(grouperService.group(eq(mockedDirectory))).thenReturn(Collections.singletonList(mockFilesGrouped()));

        List<FilesGroupedByExtension> filesGrouped = subject.scrape(GITHUB_DOMAIN);

        Assertions.assertThat(filesGrouped)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .anyMatch(file -> file.getExtension().equals("java") &&
                        file.getCount() == 10L &&
                        file.getBytes() == 555777L &&
                        file.getLines() == 15L);

        verify(uriValidatorService).validate(eq(GITHUB_DOMAIN));
        verify(directoryEnumeratorService).enumerate(eq(githubURI));
        verify(grouperService).group(eq(mockedDirectory));

    }

    private Directory mockDirectory(URI path) {

        Directory directory = new Directory();
        directory.setPath(path);

        return directory;
    }

    private FilesGroupedByExtension mockFilesGrouped(){
        return new FilesGroupedByExtension("java", 10L, 15L, 555777L);
    }
}