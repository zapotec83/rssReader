package es.jorider.rssreader.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jorge
 */
public class TimeUtil {

    public static final String TAG = TimeUtil.class.getName();

    private static final SimpleDateFormat sdfRSS = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
    private static final SimpleDateFormat sdfAPP = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat sdfShow = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Method to store the info of the time in the database to be able to restore it easier
     *
     * @param rssDate       String
     * @return
     */
    public static String dateFromRssToApp(String rssDate) {

        Date d = new Date(System.currentTimeMillis());
        String resp = sdfAPP.format(d);

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
    public static String dateFromAppToShow(String appDate) {
        Date d = new Date(System.currentTimeMillis());
        String resp = sdfShow.format(d);

        try {
            Date date = sdfAPP.parse(appDate);
            resp = sdfShow.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date");
        }
        return resp;
    }
}
