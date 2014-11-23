package es.jorider.rssreader.interfaces;

import java.util.List;

import es.jorider.rssreader.model.RssItem;

/**
 * Created by jorge
 */
public interface OnDownloadImages {

    /**
     *
     */
    public void onImagesDownloaded(List<RssItem> listItems);
}
