package es.jorider.rssreader.ui.fragment;

/**
 * Created by jorge
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.jorider.rssreader.R;
import es.jorider.rssreader.constants.ConstantsAPP;
import es.jorider.rssreader.interfaces.OnFetchedNews;
import es.jorider.rssreader.model.RssItem;
import es.jorider.rssreader.model.database.DBRssItem;
import es.jorider.rssreader.ui.activities.WebViewActivity;
import es.jorider.rssreader.util.FetchNews;
import es.jorider.rssreader.util.NetworkUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class RssReader extends Fragment implements OnFetchedNews {

    public final String TAG = RssReader.class.getName();

    private FetchNews fetchNews = null;

    private TableLayout rssTable = null;
    private Context context = null;
    private ProgressDialog dialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        context = getActivity();
        rssTable = (TableLayout) rootView.findViewById(R.id.tableLayout1);

        fetchNews = new FetchNews(context);

        dialog = ProgressDialog.show(context, context.getString(R.string.wait_please),
                context.getString(R.string.getting_news), true, true);

        // If network is available, we download the news from the web, otherwise we load the last ones from DB7
        if (NetworkUtils.isNetworkAvailable(context)) {
            fetchNews.getNewsFromHost(this);
        } else {
            Toast.makeText(context, context.getString(R.string.error_no_network_available), Toast.LENGTH_LONG).show();
            DBRssItem tableRssNew = new DBRssItem(context);
            List<RssItem> listNews = tableRssNew.getAll();
            createTableFromList (listNews);
        }
        return rootView;
    }

    @Override
    public void onNewsFetched(List<RssItem> listHost) {

        DBRssItem db = new DBRssItem(context);
        db.deleteAll();
        for(RssItem rssItem:listHost) {
            db.insert(rssItem);
        }
        List<RssItem> newList = db.getAll();
        createTableFromList(newList);
    }

    @Override
    public void onNoReachableHost(String message) {
        Toast.makeText(context, context.getString(R.string.error_host_no_reachable), Toast.LENGTH_LONG).show();
        closelDialog();
    }

    /**
     * We create the TableRows from the list arrived
     *
     * @param list
     */
    public void createTableFromList(List<RssItem> list) {

        for (final RssItem item : list) {

            TableRow row = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.rss_row_2, null);
            ((TextView) row.findViewById(R.id.title_rss)).setText(item.getTitle());
            ((TextView) row.findViewById(R.id.short_description)).setText(item.getDescription());
            ((ImageView) row.findViewById(R.id.image)).setImageBitmap(item.getImage());
            rssTable.addView(row);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtils.isNetworkAvailable(context)) {
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra(ConstantsAPP.RSS_ITEM, item);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, context.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        closelDialog();
        rssTable.requestLayout();
    }


    /**
     * Method to close the Dialog
     */
    public void closelDialog() {
        if (!getActivity().isFinishing() && dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
