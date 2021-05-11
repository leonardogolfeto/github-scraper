package com.trustly.github.scrapper.service;

import static com.trustly.github.scrapper.utils.TestConstants.TEST_BLOB_REPO_URI;
import static com.trustly.github.scrapper.utils.TestUtils.mockFile;
import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.trustly.github.scrapper.domain.File;

import io.quarkus.test.junit.QuarkusTest;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@QuarkusTest
class FileEnumeratorServiceImplTest {

    @Inject
    public FileEnumeratorServiceImpl subject;

    @Test
    void shouldEnumerateWithSuccessForMultiplesFiles() {

        Set<File> files = new HashSet<>();

        files.add(mockFile(null, null, null, TEST_BLOB_REPO_URI + ".gitignore"));
        files.add(mockFile(null, null, null, TEST_BLOB_REPO_URI + "LICENSE"));
        files.add(mockFile(null, null, null, TEST_BLOB_REPO_URI + "README.md"));

        subject.enumerate(files);

        assertThat(files)
                .isNotEmpty()
                .isNotNull()
                .hasSize(3)
                .anyMatch(file -> file.getExtension().equals("md") &&
                        file.getSize() == 22L &&
                        file.getLines() == 1L &&
                        file.getPath().equals(URI.create(TEST_BLOB_REPO_URI + "README.md")))
                .anyMatch(file -> file.getExtension().equals("OTHERS") &&
                        file.getSize() == 11366L &&
                        file.getLines() == 201L &&
                        file.getPath().equals(URI.create(TEST_BLOB_REPO_URI + "LICENSE")))
                .anyMatch(file -> file.getExtension().equals("gitignore") &&
                        file.getSize() == 278L &&
                        file.getLines() == 23L &&
                        file.getPath().equals(URI.create(TEST_BLOB_REPO_URI + ".gitignore")));

    }
}