package es.jorider.rssreader.ui.fragment;

/**
 * Created by jorge
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.jorider.rssreader.R;
import es.jorider.rssreader.adapter.RssAdapter;
import es.jorider.rssreader.interfaces.OnFetchedNews;
import es.jorider.rssreader.model.RssItem;
import es.jorider.rssreader.thread.LoadFromDatabase;
import es.jorider.rssreader.util.FetchNews;
import es.jorider.rssreader.util.NetworkUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class RssReader extends Fragment implements OnFetchedNews, SwipeRefreshLayout.OnRefreshListener{

    public final String TAG = RssReader.class.getName();

    private FetchNews fetchNews = null;

    private ProgressDialog dialog = null;
    private ListView listView = null;
    private RssAdapter adapter = null;

    private List<RssItem> listNews = null;
    private SwipeRefreshLayout swipeList = null;

    private Context context = null;

    private Handler dbHandler = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        listNews = new ArrayList<>();
        createUIThreadHandler();

        swipeList = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_list);
        swipeList.setOnRefreshListener(this);
        swipeList.setColorSchemeColors(getResources().getColor(R.color.app_color), getResources().getColor(R.color.app_color_light), getResources().getColor(R.color.app_dark));

        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new RssAdapter(context, listNews);
        listView.setAdapter(adapter);

        dialog = ProgressDialog.show(context, context.getString(R.string.wait_please), context.getString(R.string.getting_news), true, false);
        getLastNewsFromHost();
        return rootView;
    }

    /**
     * Create Thread to manage the DB
     */
    public void createUIThreadHandler() {
        dbHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                listNews.clear();
                ArrayList list = inputMessage.getData().getParcelableArrayList("LIST");
                listNews.addAll(list);
                updateListUI();
            }
        };
    }

    /**
     * Get the last news from the server
     */
    public void getLastNewsFromHost() {

        fetchNews = new FetchNews(context);

        // If network is available, we download the news from the web, otherwise we load the last ones from DB
        if (NetworkUtils.isNetworkAvailable(context)) {
            fetchNews.getNewsFromHost(this);
        } else {
            Toast.makeText(context, context.getString(R.string.error_no_network_available), Toast.LENGTH_LONG).show();
            new LoadFromDatabase(context, dbHandler, null).run();
        }
    }

    @Override
    public void onNewsFetched(List<RssItem> listHost) {
        Thread t = new Thread(new LoadFromDatabase(context, dbHandler, listHost));
        t.start();
    }

    @Override
    public void onNoReachableHost(String message) {
        Toast.makeText(context, context.getString(R.string.error_host_no_reachable), Toast.LENGTH_LONG).show();
    }

    /**
     * Update the UI
     */
    public void updateListUI() {
        if(dialog != null && dialog.isShowing() && !getActivity().isFinishing()) {
            dialog.dismiss();
        }
        listView.setVisibility(View.VISIBLE);
        swipeList.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        Toast.makeText(context, context.getString(R.string.getting_news), Toast.LENGTH_SHORT).show();
        listView.setVisibility(View.INVISIBLE);
        swipeList.setRefreshing(true);
        getLastNewsFromHost();
    }
}
