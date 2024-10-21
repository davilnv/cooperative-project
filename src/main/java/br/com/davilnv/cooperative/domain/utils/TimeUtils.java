package br.com.davilnv.cooperative.domain.utils;

import java.time.LocalDate;
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
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.parse(dateTime, StaticsUtils.DATE_TIME_FORMATTER);
    }

    public static String getStringFromLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(StaticsUtils.DATE_TIME_FORMATTER);
    }

    public static LocalDate getLocalDateFromString(String dateTime) {
        return LocalDate.parse(dateTime, StaticsUtils.DATE_FORMATTER);
    }

    public static String getStringFromLocalDate(LocalDate dateTime) {
        return dateTime.format(StaticsUtils.DATE_FORMATTER);
    }
}
