package es.jorider.rssreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.logging.Handler;

import es.jorider.rssreader.R;
import es.jorider.rssreader.model.RssItem;
import es.jorider.rssreader.ui.activities.DetailedActivity;

/**
 * Created by jorge
 */
public class RssAdapter extends BaseAdapter {

    private Context context = null;
    private List<RssItem> listNews = null;
    private LayoutInflater inflater = null;

    public RssAdapter(Context context, List<RssItem> listNews) {
        this.context = context;
        this.listNews = listNews;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listNews.size();
    }

    @Override
    public Object getItem(int i) {
        return listNews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        NotificationView notificationView = null;

        if (view == null) {
            notificationView = new NotificationView();
            view = inflater.inflate(R.layout.rss_row, null, false);

            notificationView.relativeRow = (RelativeLayout) view.findViewById(R.id.relative_row);
            notificationView.image = (ImageView) view.findViewById(R.id.image);
            notificationView.title = (TextView) view.findViewById(R.id.title_rss);
            view.setTag(notificationView);
        } else {
            notificationView = (NotificationView) view.getTag();
        }

        final RssItem rssItem = listNews.get(i);
        if(rssItem != null) {
            notificationView.title.setText(rssItem.getTitle());
            if(rssItem.getImage() != null) {
                notificationView.image.setImageBitmap(rssItem.getImage());
            } else {
                notificationView.image.setImageResource(android.R.color.transparent);
            }

            notificationView.relativeRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailedIntent = new Intent(context, DetailedActivity.class);
                    detailedIntent.putExtra(DetailedActivity.RSS_ITEM, rssItem);
                    context.startActivity(detailedIntent);
                }
            });
        }
        return view;
    }


    class NotificationView {
        private ImageView image = null;
        private TextView title = null;
        private RelativeLayout relativeRow = null;
    }

}
