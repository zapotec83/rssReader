package es.jorider.rssreader.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jorge on 23/11/14.
 */
public class TimeUtil {

    public static final String TAG = TimeUtil.class.getName();

    private static final SimpleDateFormat sdfRSS = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
    private static final SimpleDateFormat sdfAPP = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * Method to store the info of the time in the database to be able to restore it easier
     *
     * @param rssDate       String
     * @return
     */
    public static String dateFromRssToApp(String rssDate) {

        String resp = getCurrentTimeAPP();

        try {
            Date date = sdfRSS.parse(rssDate);
            resp = sdfAPP.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date");
        }
        return resp;
    }

    /**
     * Method to return the date to be able to be understood from the app
     *
     * @param appDate       String
     * @return
     */
    public static String dateFromAppToRss(String appDate) {
        String resp = getCurrentTimeRSS();

        try {
            Date date = sdfAPP.parse(appDate);
            resp = sdfRSS.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date");
        }
        return resp;
    }

    /**
     * Method to return the current time for APP
     *
     * @return
     */
    private static String getCurrentTimeAPP() {
        Date date = new Date(System.currentTimeMillis());
        return sdfAPP.format(date);
    }

    /**
     * Method to return the current time for RSS
     *
     * @return
     */
    private static String getCurrentTimeRSS() {
        Date date = new Date(System.currentTimeMillis());
        return sdfRSS.format(date);
    }

}
