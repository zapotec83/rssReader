package es.jorider.rssreader.thread;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import es.jorider.rssreader.model.RssItem;
import es.jorider.rssreader.model.database.DBRssItem;

/**
 * Created by jorge
 */
public class LoadFromDatabase implements Runnable {

    private DBRssItem dbRss = null;
    private List<RssItem> rssList = null;
    private Handler handler = null;

    public LoadFromDatabase(Context context, Handler handler, List<RssItem> list) {
        this.dbRss = new DBRssItem(context);
        this.rssList = list;
        this.handler = handler;
    }

    @Override
    public void run() {
        if(rssList != null && rssList.size() > 0) {
            dbRss.deleteAll();
            for (RssItem rssItem : rssList) {
                dbRss.insert(rssItem);
            }
        }
        ArrayList<RssItem> listFromDB = dbRss.getAll();
        Message msg = new Message();
        Bundle extra = new Bundle();
        extra.putParcelableArrayList("LIST", listFromDB);
        msg.setData(extra);
        handler.sendMessage(msg);
    }
}
