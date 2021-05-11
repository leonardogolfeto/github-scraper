package com.trustly.github.scrapper.domain;

public class FilesGroupedByExtension {

    private final String extension;
    private Long count;
    private Long lines;
    private Long bytes;

    public FilesGroupedByExtension(String extension, Long count, Long lines, Long bytes) {
        this.extension = extension;
        this.count = count;
        this.lines = lines;
        this.bytes = bytes;
    }

    public String getExtension() {
        return extension;
    }

    public Long getCount() {
        return count;
    }


    public Long getLines() {
        return lines;
    }


    public Long getBytes() {
        return bytes;
    }


    public void incrementCount(){
        this.count += 1;
    }

    public void incrementLines(Long lines){
        this.lines += lines;
    }

    public void incrementBytes(Long bytes) {
        this.bytes += bytes;
    }
}
