package com.trustly.github.scrapper.service;

import com.trustly.github.scrapper.domain.FilesGroupedByExtension;

import java.util.List;

public interface ScrapeService {

    List<FilesGroupedByExtension> scrape(String repositoryURI);

}
