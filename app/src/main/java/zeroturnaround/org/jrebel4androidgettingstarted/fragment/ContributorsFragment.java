package zeroturnaround.org.jrebel4androidgettingstarted.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;
import timber.log.Timber;
import zeroturnaround.org.jrebel4androidgettingstarted.ContributorsApplication;
import zeroturnaround.org.jrebel4androidgettingstarted.Henson;
import zeroturnaround.org.jrebel4androidgettingstarted.R;
import zeroturnaround.org.jrebel4androidgettingstarted.adapter.ContributorsAdapter;
import zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl.ImageLoader;
import zeroturnaround.org.jrebel4androidgettingstarted.service.Contributor;
import zeroturnaround.org.jrebel4androidgettingstarted.service.ContributorsService;

/**
 * Created by Sten on 17/02/16.
 */
public class ContributorsFragment extends Fragment implements ContributorsService.ContributorsListener {

    @BindView(R.id.contributors_list) ListView contributorsListView;

    private ContributorsAdapter contributorsAdapter;
    private ContributorsService contributorService;
    private List<Contributor> contributors;

    @InjectExtra String repositoryQuery;
    @InjectExtra String companyQuery;

    @State boolean useCache = false;

    public ContributorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContributorsFragment.
     */
    public static ContributorsFragment newInstance() {
        ContributorsFragment fragment = new ContributorsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contributorService = ((ContributorsApplication) getActivity().getApplicationContext()).getContributorService();
        contributorService.addListener(this);
        Icepick.restoreInstanceState(this, savedInstanceState);
        Dart.inject(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contributors, container, false);
        ButterKnife.bind(this, rootView);
        contributorsAdapter = new ContributorsAdapter(getActivity(), new ImageLoader());
        contributorsListView.setAdapter(contributorsAdapter);

        contributorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentContributorActivity = Henson.with(getActivity()).gotoContributorActivity().build();
                Intent intentContributorFragment = Henson.with(getActivity()).gotoContributorFragment().contributor(contributors.get(position)).build();
                intentContributorActivity.putExtras(intentContributorFragment);

                startActivity(intentContributorActivity);
            }
        });


        if (useCache) {
            contributorService.requestCachedContributors();
        } else {
            contributorService.requestContributors(companyQuery, repositoryQuery);
        }

        return rootView;
    }

    @Override
    public void onContributorsLoaded(List<Contributor> contributors) {
        this.contributors = contributors;
        if (contributorsAdapter != null) {
            contributorsAdapter.setContributorListAndNotify(contributors);
        }
        useCache = true;
    }

    @Override
    public void onContributorsLoadFailed(String message) {
        Timber.d("Failed " + message);

    }

    @Override
    public void onDestroy() {
        contributorService.removeListener(this);
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onContributorLoaded(Contributor contributor) {
    }
}
