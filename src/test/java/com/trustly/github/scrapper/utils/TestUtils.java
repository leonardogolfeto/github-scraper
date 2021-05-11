package com.trustly.github.scrapper.utils;

import com.trustly.github.scrapper.domain.File;

import java.net.URI;

public class TestUtils {

    public static File mockFile(String extension, Long size, Long lines, String path) {

        File result = new File();
        result.setPath(URI.create(path));
        result.setExtension(extension);
        result.setSize(size);
        result.setLines(lines);

        return result;
    }

}
