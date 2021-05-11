package com.trustly.github.scrapper.service.strategy;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public enum UnitConverter {

    BYTES(info -> info.contains(Constants.BYTES_UNIT), info -> stringToLongBytes(info, Constants.BYTES_UNIT, 1D)),
    KB(info -> info.contains(Constants.KB_UNIT), info -> stringToLongBytes(info, Constants.KB_UNIT, 1024D)),
    MB(info -> info.contains(Constants.MB_UNIT), info -> stringToLongBytes(info, Constants.MB_UNIT, 1048576D)),
    GB(info -> info.contains(Constants.GB_UNIT), info -> stringToLongBytes(info, Constants.GB_UNIT, 1073741824D));

    private final Predicate<String> rule;
    private final Function<String, Long> strategy;

    UnitConverter(Predicate<String> rule, Function<String, Long> strategy) {
        this.rule = rule;
        this.strategy = strategy;
    }

    public static Long convertSizeByUnit(String info) {

        return Arrays.stream(UnitConverter.values())
                .filter(unit -> unit.rule.test(info))
                .findFirst()
                .map(unit -> unit.strategy.apply(info))
                .orElse(0L);

    }

    private static Long stringToLongBytes(String info, String unit, Double multiplicationFactory) {

        return stringToLong(handleInformation(info, unit), multiplicationFactory);
    }

    private static String handleInformation(String info, String unit) {

        return info.replace(unit, "").trim();
    }

    private static Long stringToLong(String info, Double multiplicationFactor) {
        return Double.valueOf(Double.parseDouble(info) * multiplicationFactor).longValue();
    }

    private static class Constants {
        public static final String BYTES_UNIT = "Bytes";
        public static final String KB_UNIT = "KB";
        public static final String MB_UNIT = "MB";
        public static final String GB_UNIT = "GB";
    }
}
