package de.androidcrypto.androidjsongsonreader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    // converts a date in format yyyy-mm-dd to a unix timestamp
    static public float dateToUnixDate(String date) {
        float unixDate = 0f;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateUnix = null;
        try {
            dateUnix = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long unixTime = (long) dateUnix.getTime() / 1000;
        unixDate = Float.valueOf(unixTime);
        return unixDate;
    }

    // converts a unix timestamp to a date in format yyyy-mm-dd
    static public String unixDateToDate(String unixDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long dateL = Long.parseLong(unixDate);
        LocalDateTime dateTimeLocal = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateL), TimeZone.getDefault().toZoneId());
        return dateTimeLocal.format(formatter);
    }
    // convert unix timestamp to date
    //long dateL = Long.parseLong(date);
    //LocalDateTime dateTimeLocal = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateL), TimeZone.getDefault().toZoneId());

}
