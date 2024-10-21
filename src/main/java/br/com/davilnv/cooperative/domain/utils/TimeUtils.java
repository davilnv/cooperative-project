package br.com.davilnv.cooperative.domain.utils;

import java.time.LocalDateTime;

public class TimeUtils {
    public static LocalDateTime getDateTimeNow() {
        return LocalDateTime.now(StaticsUtils.ZONE_ID);
    }

    public static LocalDateTime getDateTimeNowPlusOneMinute() {
        return LocalDateTime.now(StaticsUtils.ZONE_ID).plusMinutes(1);
    }

    public static LocalDateTime getDateTimeNowPlusMinutes(int minutes) {
        return LocalDateTime.now(StaticsUtils.ZONE_ID).plusMinutes(minutes);
    }

    public static LocalDateTime getLocalDateTimeFromString(String dateTime) {
        return LocalDateTime.parse(dateTime, StaticsUtils.DATE_TIME_FORMATTER);
    }

    public static String getStringFromLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(StaticsUtils.DATE_TIME_FORMATTER);
    }
}
