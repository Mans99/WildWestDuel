package qdesign.project.util;

import android.icu.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class Timestamps {
    public static String currentTimeStamp(){
        Random rand = new Random();
        int randomTimeSec = rand.nextInt(6) + 5;
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.SECOND, randomTimeSec);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    public static long unixTimeStamp(){
        Date date = new Date();
        long unixTimeInMillis = date.getTime();
        long unixTimeInSeconds = unixTimeInMillis / 1000;
        long newUnixTimeInSeconds = unixTimeInSeconds;
        return newUnixTimeInSeconds * 1000;
    }
}
