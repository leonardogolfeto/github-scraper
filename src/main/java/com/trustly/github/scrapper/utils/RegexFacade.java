package com.trustly.github.scrapper.utils;

import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class RegexFacade {

    public static final String ROW_PATTERN = "<div role=\"row\"[\\S\\s]*?>[\\S\\s]*?</div>[\\S\\s]*?</div>[\\S\\s]*?</div>[\\S\\s]*?</div>";
    public static final String ROW_HEADER_PATTERN = "<div role=\"rowheader\"[\\S\\s]*?>[\\S\\s]*?</div>";
    public static final String FILE_INFO_TARGET_PATTERN = "<div class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1\">[\\S\\s]*?</div>";
    public static final String FILE_FINAL_PATH_PATTERN = "<strong class=\"final-path\">[\\S\\s]*?</strong>";

    public String findSingleResult(String text, String pattern) {

        Matcher matcher = getMatcherBy(text, pattern);
        if(matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public List<String> findMultiplesResults(String body, String pattern) {

        List<String> result = new ArrayList<>();
        Matcher matcher = getMatcherBy(body, pattern);

        while (matcher.find()){
            result.add(matcher.group());
        }

        return result;
    }

    public String findTextBetween(String text, String init, String end) {
        return text.split(init)[1].split(end)[0];
    }


    private Matcher getMatcherBy(String body, String rowPattern) {
        Pattern pattern = Pattern.compile(rowPattern);
        return pattern.matcher(body);
    }
}
