package es.jorider.rssreader.ui.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.jorider.rssreader.R;
import es.jorider.rssreader.model.RssItem;
import es.jorider.rssreader.util.ImageUtils;
import es.jorider.rssreader.util.TimeUtil;

/**
 * Created by jorge
 */
public class DetailedActivity extends ActionBarActivity {

    public static final String TAG = DetailedActivity.class.getName();

    public static final String RSS_ITEM = "es.jorider.rssreader.ui.activities.rss_item";

    private Context context = null;

    private RssItem rssItem = null;
    private TextView date, title, description = null;
    private ImageView image = null;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.detailed_activity);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            rssItem = extras.getParcelable(RSS_ITEM);

            date = (TextView) findViewById(R.id.date);
            title = (TextView) findViewById(R.id.rss_title);
            description = (TextView) findViewById(R.id.rss_description);
            image = (ImageView) findViewById(R.id.image_rss);

            if(rssItem != null) {
                date.setText(TimeUtil.dateFromAppToShow(rssItem.getDate()));
                title.setText(rssItem.getTitle());
                description.setText(rssItem.getDescription());
                if(rssItem.getImage() != null) {
                    Bitmap imageBitmap = rssItem.getImage();
                    image.setImageBitmap(ImageUtils.getResizeBitmap(imageBitmap, 2));
                }else
                    image.setImageResource(android.R.color.transparent);
            }

        } else {
            Toast.makeText(context, "No data arrived!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
