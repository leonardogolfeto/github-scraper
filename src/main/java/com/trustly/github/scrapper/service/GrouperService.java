package com.trustly.github.scrapper.service;

import com.trustly.github.scrapper.domain.Directory;
import com.trustly.github.scrapper.domain.FilesGroupedByExtension;

import java.util.List;

public interface GrouperService {
    List<FilesGroupedByExtension> group(Directory rootDirectory);
}
