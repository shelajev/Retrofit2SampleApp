package zeroturnaround.org.jrebel4androidgettingstarted.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.f2prateek.dart.HensonNavigable;

import zeroturnaround.org.jrebel4androidgettingstarted.R;
import zeroturnaround.org.jrebel4androidgettingstarted.fragment.ContributorsFragment;

@HensonNavigable public class ContributorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContributorsFragment contributorsFragment = ContributorsFragment.newInstance();
        contributorsFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().
            beginTransaction().add(R.id.fragment_holder, contributorsFragment, ContributorsFragment.class.getName());

        transaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
