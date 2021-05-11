package com.trustly.github.scrapper.domain;

import java.net.URI;
import java.util.Objects;

public class File {

    private URI path;
    private Long size;
    private Long lines;
    private String extension;

    public URI getPath() {
        return path;
    }

    public void setPath(URI path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getLines() {
        return lines;
    }

    public void setLines(Long lines) {
        this.lines = lines;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
