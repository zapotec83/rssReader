package es.jorider.rssreader.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import es.jorider.rssreader.R;
import es.jorider.rssreader.constants.ConstantsAPP;
import es.jorider.rssreader.model.RssItem;

/**
 * Created by jorge
 */
public class WebViewActivity extends ActionBarActivity {

    public static final String TAG = WebViewActivity.class.getName();

    private Context context = null;

    private WebView webView = null;
    private RssItem rssItem = null;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        context = this;

        setContentView(R.layout.webview_activity);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            rssItem = extras.getParcelable(ConstantsAPP.RSS_ITEM);

            final WebView webView = (WebView) findViewById(R.id.webView);

            webView.loadUrl(rssItem.getLink());

        } else {
            Toast.makeText(context, "No data arrived!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.open_in_browser) {
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(rssItem.getLink()));
            startActivity(browser);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
