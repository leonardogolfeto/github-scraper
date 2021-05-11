package com.trustly.github.scrapper.service;

import javax.inject.Singleton;

import com.trustly.github.scrapper.domain.Directory;
import com.trustly.github.scrapper.domain.FilesGroupedByExtension;

import io.quarkus.cache.CacheResult;
import java.net.URI;
import java.util.List;

@Singleton
public class ScrapeServiceImpl implements ScrapeService {

    private final URIValidatorService uriValidatorService;
    private final DirectoryEnumeratorService directoryEnumeratorService;
    private final GrouperService grouperService;

    public ScrapeServiceImpl(URIValidatorService uriValidatorService,
                             DirectoryEnumeratorService directoryEnumeratorService,
                             GrouperService grouperService){

        this.uriValidatorService = uriValidatorService;
        this.directoryEnumeratorService = directoryEnumeratorService;
        this.grouperService = grouperService;
    }

    @Override
    @CacheResult(cacheName = "scrape-cache")
    public List<FilesGroupedByExtension> scrape(String repositoryURI) {
        URI validatedURI = uriValidatorService.validate(repositoryURI);
        Directory rootDirectory = directoryEnumeratorService.enumerate(validatedURI);
        return grouperService.group(rootDirectory);
    }
}
