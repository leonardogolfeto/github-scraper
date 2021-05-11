package com.trustly.github.scrapper.service;

import static com.trustly.github.scrapper.utils.TestConstants.GITHUB_DOMAIN;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.trustly.github.scrapper.domain.Directory;
import com.trustly.github.scrapper.domain.File;
import com.trustly.github.scrapper.domain.FilesGroupedByExtension;

import java.net.URI;
import java.util.List;

class GrouperServiceImplTest {

    private GrouperServiceImpl subject;

    @BeforeEach
    void setUp(){
        subject = new GrouperServiceImpl();
    }

    @Test
    void shouldGroupRepositoryByExtensionWithSuccess() {

        Directory rootDirectory = mockRootDirectory();

        List<FilesGroupedByExtension> groupedFiles = subject.group(rootDirectory);

        Assertions.assertThat(groupedFiles)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .anyMatch(group -> group.getExtension().equals("java") &&
                          group.getLines() == 30L &&
                          group.getBytes() == 600L &&
                          group.getCount() == 3L)
                .anyMatch(group -> group.getExtension().equals("xml") &&
                        group.getLines() == 45L &&
                        group.getBytes() == 450L &&
                        group.getCount() == 3L);
    }

    private Directory mockRootDirectory() {

        Directory rootDirectory = new Directory();

        rootDirectory.getFiles().add(mockFile("java", 200L, 10L, GITHUB_DOMAIN + "/3"));
        rootDirectory.getFiles().add(mockFile("xml", 150L, 15L, GITHUB_DOMAIN + "/4"));

        rootDirectory.getDirectories().add(mockDirectory());

        rootDirectory.getDirectories()
                .stream()
                .findFirst()
                .ifPresent(directory -> directory.getDirectories().add(mockDirectory()));

        return rootDirectory;

    }

    private Directory mockDirectory() {

        Directory directory = new Directory();
        directory.getFiles().add(mockFile("java", 200L, 10L, GITHUB_DOMAIN + "/1"));
        directory.getFiles().add(mockFile("xml", 150L, 15L,  GITHUB_DOMAIN + "/2"));
        return directory;
    }

    private File mockFile(String extension, long size, long lines, String path) {

        File result = new File();
        result.setPath(URI.create(path));
        result.setExtension(extension);
        result.setSize(size);
        result.setLines(lines);

        return result;
    }
}