package es.jorider.rssreader.ui.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import es.jorider.rssreader.R;
import es.jorider.rssreader.constants.ConstantsCon;
import es.jorider.rssreader.constants.ConstantsPref;
import es.jorider.rssreader.ui.fragment.RssReader;

/**
 * @author jorge
 */
public class MainActivity extends ActionBarActivity {

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new RssReader()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences preferences = getSharedPreferences(ConstantsPref.PREF_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor prefEditor = preferences.edit();

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.change_url));
            final EditText input = new EditText(context);
            input.setText(preferences.getString(ConstantsPref.HOST_NAME, ConstantsCon.DEFAULT_URL));
            builder.setView(input);
            builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    prefEditor.putString(ConstantsPref.HOST_NAME, input.getText().toString());
                    prefEditor.commit();
                    finish();
                    startActivity(new Intent(context, MainActivity.class));
                }
            });
            builder.setCancelable(true);
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
