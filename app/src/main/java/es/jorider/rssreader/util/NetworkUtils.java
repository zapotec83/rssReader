package es.jorider.rssreader.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by jorge
 */
public class NetworkUtils {

    /**
     * Check whether connection is available or not
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

}
