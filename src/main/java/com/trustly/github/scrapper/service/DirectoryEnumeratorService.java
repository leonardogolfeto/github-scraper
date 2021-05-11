package com.trustly.github.scrapper.service;

import com.trustly.github.scrapper.domain.Directory;

import java.net.URI;

interface DirectoryEnumeratorService {
    Directory enumerate(URI repositoryURI);
}
