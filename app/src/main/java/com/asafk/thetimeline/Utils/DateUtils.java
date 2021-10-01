package com.asafk.thetimeline.Utils;

import java.util.Calendar;

public class DateUtils {

    /**
     * Take time in ms and convert it to int with the format yyyyMMdd
     * @param timeInMs
     * @return
     */
    public static int timeInMsToIntFormat(long timeInMs){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMs);

        int formattedTime = 0;

        formattedTime += calendar.get(Calendar.YEAR) * Math.pow(10, 4);
        formattedTime += calendar.get(Calendar.MONTH) * Math.pow(10, 2);
        formattedTime += calendar.get(Calendar.DAY_OF_MONTH);

        return formattedTime;
    }

    public static int timeInIntToMsFormat(long timeInMs){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMs);

        int formattedTime = 0;

        formattedTime += calendar.get(Calendar.YEAR) * Math.pow(10, 4);
        formattedTime += calendar.get(Calendar.MONTH) * Math.pow(10, 2);
        formattedTime += calendar.get(Calendar.DAY_OF_MONTH);

        return formattedTime;
    }
}
