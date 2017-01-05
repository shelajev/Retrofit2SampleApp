package zeroturnaround.org.jrebel4androidgettingstarted.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import zeroturnaround.org.jrebel4androidgettingstarted.R;
import zeroturnaround.org.jrebel4androidgettingstarted.fragment.ContributorFragment;
import zeroturnaround.org.jrebel4androidgettingstarted.fragment.ContributorsFragment;

import com.f2prateek.dart.HensonNavigable;



@HensonNavigable public class ContributorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContributorFragment contributorFragment = ContributorFragment.newInstance();
        contributorFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().
                beginTransaction().add(R.id.fragment_holder, contributorFragment, ContributorsFragment.class.getName());

        transaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
