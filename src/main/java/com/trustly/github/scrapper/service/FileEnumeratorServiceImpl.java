package com.trustly.github.scrapper.service;

import static com.trustly.github.scrapper.utils.RegexFacade.FILE_FINAL_PATH_PATTERN;
import static com.trustly.github.scrapper.utils.RegexFacade.FILE_INFO_TARGET_PATTERN;

import javax.inject.Singleton;

import com.trustly.github.scrapper.domain.File;
import com.trustly.github.scrapper.service.strategy.UnitConverter;
import com.trustly.github.scrapper.utils.HttpClientFacade;
import com.trustly.github.scrapper.utils.RegexFacade;

import java.util.Set;

@Singleton
public class FileEnumeratorServiceImpl implements FileEnumeratorService {

    private static final String OTHERS = "OTHERS";

    private final HttpClientFacade httpClientFacade;
    private final RegexFacade regexFacade;

    public FileEnumeratorServiceImpl(HttpClientFacade httpClientFacade, RegexFacade regexFacade) {
        this.httpClientFacade = httpClientFacade;
        this.regexFacade = regexFacade;
    }

    @Override
    public void enumerate(Set<File> files) {

        files.forEach(file -> {

            String body = httpClientFacade.sendGetTo(file.getPath());

            String fileInformation = regexFacade.findSingleResult(body, FILE_INFO_TARGET_PATTERN);
            String fileFinalPath = regexFacade.findSingleResult(body, FILE_FINAL_PATH_PATTERN);

            file.setSize(findFileSize(fileInformation));
            file.setLines(findLines(fileInformation));
            file.setExtension(findExtension(fileFinalPath));

        });
    }

    private String findExtension(String path) {

        if (path.contains(".")) {
            String[] stringArray = regexFacade.findTextBetween(path, ">", "<").split("\\.");
            return stringArray[stringArray.length -1];
        }
        return OTHERS;

    }

    private Long findLines(String fileInformation) {

        if(fileInformation.contains("lines")) {
            return Long.valueOf(
                    regexFacade.findTextBetween(fileInformation, ">", "\\(").replace("lines", "").trim()
            );
        }
        return 0L;
    }

    private Long findFileSize(String fileInformation) {
        return UnitConverter.convertSizeByUnit(handleInformation(fileInformation));
    }

    private String handleInformation(String info) {

        if(info.contains("</span>")) {
            return regexFacade.findTextBetween(info, "</span>", "<");
        }
        return regexFacade.findTextBetween(info, ">", "<");
    }
}
