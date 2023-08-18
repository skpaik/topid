package io.goribco.apis.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    public static Date convertToDate(long moment) {
        return new Date(moment);
    }

    public static String convertToDateStr(long moment) {
        DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");

        Date currentDate = convertToDate(moment);

        return df.format(currentDate);
    }
}
