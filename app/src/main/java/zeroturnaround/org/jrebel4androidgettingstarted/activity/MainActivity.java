package zeroturnaround.org.jrebel4androidgettingstarted.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import zeroturnaround.org.jrebel4androidgettingstarted.R;
import zeroturnaround.org.jrebel4androidgettingstarted.fragment.ContributorsFragment;
import zeroturnaround.org.jrebel4androidgettingstarted.fragment.SearchFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(SearchFragment.class.getName()) == null) {
            SearchFragment searchFragment = SearchFragment.newInstance();
            searchFragment.setRetainInstance(true);

            FragmentTransaction transaction = getSupportFragmentManager().
                    beginTransaction().add(R.id.fragment_holder, searchFragment, SearchFragment.class.getName());

            transaction.commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
