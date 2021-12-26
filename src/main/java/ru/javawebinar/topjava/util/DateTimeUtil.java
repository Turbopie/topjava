package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.time.LocalTime.parse;

public class DateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);

    public static boolean isBetweenHalfOpen(LocalTime lt, @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        return (startTime == null || lt.compareTo(startTime) >= 0) && (endTime == null || lt.compareTo(endTime) < 0);
    }

    public static LocalDateTime atStartOfDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static <T> T converterFromString(Class<T> t, String parameter, HttpServletRequest request) {
        if(t == LocalDate.class) {
            return StringUtils.hasLength(request.getParameter(parameter)) ?
                    t.cast(LocalDate.parse(request.getParameter(parameter))) :
                    null;
        } else {
            return StringUtils.hasLength(request.getParameter(parameter)) ?
                    t.cast(parse(request.getParameter(parameter))) :
                    null;
        }
    }

    /*
    public static @Nullable
    LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.hasLength(str) ? LocalDate.parse(str) : null;
    }

    public static @Nullable
    LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.hasLength(str) ? parse(str) : null;
    }

     */


}

