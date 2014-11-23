package es.jorider.rssreader.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.List;

import es.jorider.rssreader.connections.DownloadImages;
import es.jorider.rssreader.connections.HostConnection;
import es.jorider.rssreader.constants.ConstantsCon;
import es.jorider.rssreader.constants.ConstantsPref;
import es.jorider.rssreader.interfaces.OnDownloadImages;
import es.jorider.rssreader.interfaces.OnFetchedNews;
import es.jorider.rssreader.model.RespServer;
import es.jorider.rssreader.model.RssItem;

/**
 * Created by jorge
 */
public class FetchNews {

    /**
     * SOURCE TO GET FROM DATABASE
     */
    public static final int GET_FROM_DB = 0;
    /**
     * SOURCE TO GET FROM HOST
     */
    public static final int GET_FROM_HOST = 1;
    public final String TAG = FetchNews.class.getName();
    private Context context = null;

    /**
     * Constructor
     *
     * @param context
     */
    public FetchNews(Context context) {
        this.context = context;
    }

    /**
     * Method to get the news
     *
     * @param listener OnFetchedNews
     */
    public void getNewsFromHost(final OnFetchedNews listener) {

        final SharedPreferences prefs = context.getSharedPreferences(ConstantsPref.PREF_NAME, Context.MODE_PRIVATE);

        new AsyncTask<Void, Void, Void>() {

            RespServer response = null;
            HostConnection fromHost = null;

            @Override
            protected Void doInBackground(Void... params) {
                fromHost = new HostConnection();
                response = fromHost.getNews(prefs.getString(ConstantsPref.HOST_NAME, ConstantsCon.DEFAULT_URL));
                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                super.onPostExecute(Void);
                if (!response.isExceptionCaught()) {

                    List<RssItem> rssItems = response.getItemList();

                    if (rssItems != null) {
                        new DownloadImages(rssItems, new OnDownloadImages() {
                            @Override
                            public void onImagesDownloaded(List<RssItem> listItems) {
                                listener.onNewsFetched(response.getItemList());
                            }
                        }).execute();
                    } else {
                        listener.onNewsFetched(response.getItemList());
                    }
                } else {
                    listener.onNoReachableHost(response.getErrorMsg());
                }
            }
        }.execute();
    }
}
