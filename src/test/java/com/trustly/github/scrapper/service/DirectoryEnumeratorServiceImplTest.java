package com.trustly.github.scrapper.service;

import static com.trustly.github.scrapper.utils.TestConstants.TEST_BLOB_REPO_URI;
import static com.trustly.github.scrapper.utils.TestConstants.TEST_TREE_REPO_URI;
import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.trustly.github.scrapper.domain.Directory;
import com.trustly.github.scrapper.domain.File;

import io.quarkus.test.junit.QuarkusTest;
import java.net.URI;
import java.util.Set;

@QuarkusTest
class DirectoryEnumeratorServiceImplTest {

    @Inject
    public DirectoryEnumeratorServiceImpl subject;

    @Test
    void shouldEnumerateAllDirectoriesFromRepository() {

        Directory result = subject.enumerate(URI.create("https://github.com/leonardogolfeto/github-scrapper-test"));

        assertThat(result.getFiles()).hasSize(3)
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
                          file.getPath().equals(URI.create(TEST_BLOB_REPO_URI + ".gitignore")));;

        assertThat(result.getDirectories())
                .hasSize(1)
                .anyMatch(dir -> dir.getPath().equals(URI.create(TEST_TREE_REPO_URI + "scripts")) &&
                        dir.getFiles().size() == 1 &&
                        getFile(dir).getExtension().equals("sql") &&
                        getFile(dir).getSize() == 296 &&
                        getFile(dir).getLines() == 2L);

        assertThat(getThirdNivelDirectories(result))
                .hasSize(1)
                .anyMatch(dir -> dir.getPath().equals(URI.create(TEST_TREE_REPO_URI + "scripts/outros")) &&
                        dir.getFiles().size() == 1 &&
                        getFile(dir).getExtension().equals("xml") &&
                        getFile(dir).getSize() == 5273L &&
                        getFile(dir).getLines() == 151L);

    }

    private File getFile(Directory dir) {
        return dir
                .getFiles()
                .stream()
                .findFirst()
                .get();
    }

    private Set<Directory> getThirdNivelDirectories(Directory result) {
        return result.getDirectories()
                .stream()
                .findFirst()
                .get()
                .getDirectories();
    }
}