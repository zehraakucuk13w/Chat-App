package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    // Returns the current time in milliseconds
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    // Converts timestamp to readable date format (optional for display)
    public static String formatTimestamp(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    // Returns the difference between two timestamps in seconds (e.g., for activity
    // check)
    public static long getTimeDifferenceInSeconds(long t1, long t2) {
        return Math.abs((t2 - t1) / 1000);
    }
}
