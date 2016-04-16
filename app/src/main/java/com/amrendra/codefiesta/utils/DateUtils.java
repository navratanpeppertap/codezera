package com.amrendra.codefiesta.utils;

import com.amrendra.codefiesta.CodeFiestaApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Amrendra Kumar on 09/04/16.
 */
public class DateUtils {

    public static long SEC_IN_ONE_MINUTE = 60;
    public static long SEC_IN_ONE_HOUR = SEC_IN_ONE_MINUTE * 60;
    public static long SEC_IN_ONE_DAY = SEC_IN_ONE_HOUR * 24;
    public static long SEC_IN_ONE_YEAR = SEC_IN_ONE_DAY * 365;

    public static long getEpochTime(String time) {
        long epochTime = -1;
        //"2013-12-08T09:00:00"
        if (null != time) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                Date date = df.parse(time);
                epochTime = date.getTime() / 1000;
            } catch (ParseException e) {
                CodeFiestaApplication.shouldNotHappen(
                        "Exception in converting time to epoch : "
                                + "time=" + time + " exception=" +
                                e.getLocalizedMessage());
            }
        } else {
            CodeFiestaApplication.shouldNotHappen("Time to convert to epoch is null");
        }
        return epochTime;
    }

    public static String epochToDateTimeGmt(long time) {
        long timems = time * 1000L;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(new Date(timems));
    }

    public static String epochToDateTimeLocal(long time) {
        long timems = time * 1000L;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(timems));
    }

    public static String epochToDateTimeLocalShow(long time) {
        long timems = time * 1000L;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(timems));
    }

    public static long getCurrentEpochTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static String getTimeLeftForEnd(long startTime, long endTime) {
        long currTime = System.currentTimeMillis() / 1000;
        if (currTime < startTime) {
            return "Starts in " + getDurationString(startTime - currTime, false);
        }
        long left = endTime - currTime;
        if (left > 0) {
            return getDurationString(left, true) + " left";
        } else {
            return "Ended";
        }
    }

    public static String getDurationString(long time, boolean secRequired) {
        StringBuilder sb = new StringBuilder();
        long years = time / SEC_IN_ONE_YEAR;
        time %= SEC_IN_ONE_YEAR;
        long days = time / SEC_IN_ONE_DAY;
        time %= SEC_IN_ONE_DAY;
        long hours = time / SEC_IN_ONE_HOUR;
        time %= SEC_IN_ONE_HOUR;
        long mins = time / SEC_IN_ONE_MINUTE;
        time %= SEC_IN_ONE_MINUTE;
        if (years > 0) {
            sb.append(Long.toString(years)).append((years > 1 ? "years " : "year "));
        }
        if (days > 0) {
            sb.append(Long.toString(days)).append((days > 1 ? "days " : "day "));
        }
        if (hours > 0) {
            sb.append(Long.toString(hours)).append((hours > 1 ? "hours " : "hour "));
        }
        if (mins > 0) {
            sb.append(Long.toString(mins)).append((mins > 1 ? "mins " : "min "));
        }
        if (secRequired && time > 0) {
            sb.append(Long.toString(time)).append((time > 1 ? "secs " : "sec "));
        }
        return sb.toString().trim();
    }
}