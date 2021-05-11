package com.trustly.github.scrapper.service;

import com.trustly.github.scrapper.domain.File;

import java.util.Set;

public interface FileEnumeratorService {
    void enumerate(Set<File> files);
}
