package com.trustly.github.scrapper.service;

import javax.inject.Singleton;

import com.trustly.github.scrapper.domain.Directory;
import com.trustly.github.scrapper.domain.File;
import com.trustly.github.scrapper.utils.HttpClientFacade;
import com.trustly.github.scrapper.utils.RegexFacade;

import io.quarkus.cache.CacheResult;
import java.net.URI;

@Singleton
class DirectoryEnumeratorServiceImpl implements DirectoryEnumeratorService {

    public static final String GITHUB = "https://github.com";

    private final HttpClientFacade httpClientFacade;
    private final RegexFacade regexFacade;
    private final FileEnumeratorService fileEnumeratorService;

    public DirectoryEnumeratorServiceImpl(HttpClientFacade httpClientFacade, RegexFacade regexFacade, FileEnumeratorService fileEnumeratorService) {
        this.httpClientFacade = httpClientFacade;
        this.regexFacade = regexFacade;
        this.fileEnumeratorService = fileEnumeratorService;
    }

    @Override
    public Directory enumerate(URI repositoryURI) {

        Directory rootDirectory = new Directory();
        rootDirectory.setPath(repositoryURI);

        return enumerateDirectory(rootDirectory);
    }

    private Directory enumerateDirectory(Directory directory) {

        String body = httpClientFacade.sendGetTo(directory.getPath());

        searchFiles(directory, body);
        searchDirectoriesRecursively(directory);

        return directory;
    }

    private void searchFiles(Directory directory, String body) {

        regexFacade.findMultiplesResults(body, RegexFacade.ROW_PATTERN).forEach(row -> findFiles(directory, row));
        fileEnumeratorService.enumerate(directory.getFiles());

    }


    private void findFiles(Directory directory, String row) {

        String rowHeader = regexFacade.findSingleResult(row, RegexFacade.ROW_HEADER_PATTERN);

        if (isFile(row)) {
            File currentFile = new File();
            currentFile.setPath(createPathBy(rowHeader));
            directory.getFiles().add(currentFile);
        }

        if (isDirectory(row)) {
            Directory currentDirectory = new Directory();
            currentDirectory.setPath(createPathBy(rowHeader));
            directory.getDirectories().add(currentDirectory);
        }
    }

    private void searchDirectoriesRecursively(Directory directory) {
        directory.getDirectories()
                .forEach(this::enumerateDirectory);
    }

    private URI createPathBy(String rowHeader) {
        return URI.create(GITHUB + regexFacade.findTextBetween(rowHeader, "href=\"", "\""));
    }

    private boolean isDirectory(String row) {
        return row.contains("aria-label=\"Directory\"");
    }

    private boolean isFile(String row) {
        return row.contains("aria-label=\"File\"");
    }

}
