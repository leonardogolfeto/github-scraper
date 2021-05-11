package com.trustly.github.scrapper.service;

import javax.inject.Singleton;

import com.trustly.github.scrapper.domain.Directory;
import com.trustly.github.scrapper.domain.File;
import com.trustly.github.scrapper.domain.FilesGroupedByExtension;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class GrouperServiceImpl implements GrouperService {

    @Override
    public List<FilesGroupedByExtension> group(Directory rootDirectory) {

        List<FilesGroupedByExtension> result = new ArrayList<>();
        groupFilesInDirectoriesRecursively(rootDirectory, result);

        return result;
    }

    private void groupFilesInDirectoriesRecursively(Directory directory, List<FilesGroupedByExtension> result) {
        
        directory.getFiles()
                .forEach(file -> searchAndIncrementGroup(result, file));
        
        directory
                .getDirectories()
                .forEach(dir -> groupFilesInDirectoriesRecursively(dir, result));

    }

    private void searchAndIncrementGroup(List<FilesGroupedByExtension> result, File file) {

        result.stream()
                .filter(filesGrouped -> filesGrouped.getExtension().equals(file.getExtension()))
                .findFirst()
                .ifPresentOrElse(filesGrouped -> incrementGroup(file, filesGrouped), createGroup(result, file));

    }

    private void incrementGroup(File file, FilesGroupedByExtension filesGrouped) {

        filesGrouped.incrementCount();
        filesGrouped.incrementBytes(file.getSize());
        filesGrouped.incrementLines(file.getLines());

    }

    private Runnable createGroup(List<FilesGroupedByExtension> result, File file) {
        return () -> result.add(new FilesGroupedByExtension(file.getExtension(), 1L, file.getLines(), file.getSize()));
    }
}
