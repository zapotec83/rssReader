package es.jorider.rssreader.interfaces;

import java.util.List;

import es.jorider.rssreader.model.RssItem;

/**
 * Created by jorge
 */
public interface OnFetchedNews {

    /**
     *
     * @param newsList
     */
    public void onNewsFetched(List<RssItem> newsList);


    /**
     * Host no reachable
     *
     * @param message   String
     *
     */
    public void onNoReachableHost(String message);
}
