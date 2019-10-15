package com.zack.gojektestjava.util;

import java.util.concurrent.TimeUnit;

public class Utilities {
    public static long compareTime(long timestamp, TimeUnit unit) {
        if (timestamp < 0) {
            return -1;
        }

        long currentTimestamp = System.currentTimeMillis();
        long diff = currentTimestamp - timestamp;
        switch (unit) {
            case DAYS:
                return TimeUnit.MILLISECONDS.toDays(diff);

            case HOURS:
                return TimeUnit.MILLISECONDS.toHours(diff);

            case MINUTES:
                return TimeUnit.MILLISECONDS.toMinutes(diff);

            case SECONDS:
                return TimeUnit.MILLISECONDS.toSeconds(diff);

            default:
                return TimeUnit.MILLISECONDS.toMillis(diff);
        }
    }
}
