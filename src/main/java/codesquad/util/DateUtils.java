package codesquad.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getCurrentTime() {
        long currentTime = System.currentTimeMillis();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(currentTime));
    }
}
