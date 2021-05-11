package com.trustly.github.scrapper.domain;

import java.net.URI;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Directory {

    private URI path;
    private final Set<File> files;
    private final Set<Directory> directories;

    public Directory() {
        this.files = new HashSet<>();
        this.directories = new HashSet<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    public URI getPath() {
        return path;
    }

    public void setPath(URI path) {
        this.path = path;
    }

    public Set<File> getFiles() {
        return files;
    }

    public Set<Directory> getDirectories() {
        return directories;
    }
}
