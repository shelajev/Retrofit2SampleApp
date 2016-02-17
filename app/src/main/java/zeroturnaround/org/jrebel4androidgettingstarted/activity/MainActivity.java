package zeroturnaround.org.jrebel4androidgettingstarted.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


import zeroturnaround.org.jrebel4androidgettingstarted.R;
import zeroturnaround.org.jrebel4androidgettingstarted.service.Contributor;
import zeroturnaround.org.jrebel4androidgettingstarted.service.ContributorsService;
import zeroturnaround.org.jrebel4androidgettingstarted.service.ContributorsService.ContributorsListener;

public class MainActivity extends AppCompatActivity implements ContributorsListener {

    private ContributorsService contributorsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Super fast hello world from JRebel for Android", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        contributorsService = new ContributorsService();
        contributorsService.addListener(this);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contributorsService.requestContributors("square", "retrofit");
                button.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        contributorsService.removeListener(this);
        super.onDestroy();
    }

    @Override
    public void onContributorsLoaded(List<Contributor> contributors) {
        final TextView textView = (TextView) findViewById(R.id.textView);
        StringBuilder stringBuilder = new StringBuilder();
        for (Contributor contributor : contributors) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(contributor);
        }
        textView.setText(stringBuilder.toString());
    }

    @Override
    public void onContributorsLoadFailed(String message) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
