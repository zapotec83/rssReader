package es.jorider.rssreader.connections;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import es.jorider.rssreader.interfaces.OnDownloadImages;
import es.jorider.rssreader.model.RssItem;
import es.jorider.rssreader.util.TimeUtil;

/**
 * Created by jorge
 */
public class DownloadImages extends AsyncTask<Void, Void, Void> {

    private List<RssItem> rssItemList = null;
    private OnDownloadImages listener = null;

    public DownloadImages(List<RssItem> rssItemList, OnDownloadImages listener) {
        this.rssItemList = rssItemList;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        //We get the data transformed into the way we want (Image and Date)
        for (int i = 0; i < rssItemList.size(); i++) {
            rssItemList.get(i).setImage(getImageFromString(rssItemList.get(i).getImageSrc()));
            rssItemList.get(i).setPubDate(TimeUtil.dateFromRssToApp(rssItemList.get(i).getPubDate()));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onImagesDownloaded(rssItemList);
    }

    /**
     *
     * @param   url         String
     * @return
     */
    public Bitmap getImageFromString(String url) {

        Bitmap bitmap = null;

        try {
            InputStream is = (InputStream) new URL(url).getContent();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
        }
        return bitmap;
    }

}
