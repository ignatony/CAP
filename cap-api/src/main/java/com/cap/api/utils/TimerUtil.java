package com.cap.api.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class TimerUtil {

    /**
     * @param fromDate
     * @param toDate
     * @return
     */
    public static String getDiff(LocalDateTime fromDate, LocalDateTime toDate) {
        LocalDateTime tempDateTime = LocalDateTime.from(fromDate);

        long minutes = tempDateTime.until(toDate, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        long seconds = tempDateTime.until(toDate, ChronoUnit.SECONDS);
        tempDateTime = tempDateTime.plusSeconds(seconds);

        long millli = tempDateTime.until(toDate, ChronoUnit.MILLIS);
        return "" + minutes + ":" + seconds + ":" + millli;
    }

    /**
     * Converting current date time to UTC format
     * @return LocalDateTime
     */
    public static LocalDateTime dateTime() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }


}
