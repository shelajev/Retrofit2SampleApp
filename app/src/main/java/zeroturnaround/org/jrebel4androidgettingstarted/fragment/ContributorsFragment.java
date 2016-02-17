package zeroturnaround.org.jrebel4androidgettingstarted.fragment;

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

import java.util.List;

import zeroturnaround.org.jrebel4androidgettingstarted.ContributorsApplication;
import zeroturnaround.org.jrebel4androidgettingstarted.R;
import zeroturnaround.org.jrebel4androidgettingstarted.adapter.ContributorsAdapter;
import zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl.FrescoImageLoader;
import zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl.GlideImageLoader;
import zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl.PicassoImageLoader;
import zeroturnaround.org.jrebel4androidgettingstarted.service.Contributor;
import zeroturnaround.org.jrebel4androidgettingstarted.service.ContributorsService;

/**
 * Created by Sten on 17/02/16.
 */
public class ContributorsFragment extends Fragment implements ContributorsService.ContributorsListener {

    private static String KEY_USE_CACHED_CONTRIBUTORS = ContributorsFragment.class.getName() + ".USE_CACHED_CONTRIBUTORS";

    private ContributorsAdapter contributorsAdapter;
    private ListView contributorsListView;
    private ContributorsService contributorService;
    private List<Contributor> contributors;

    public ContributorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContributorsFragment.
     */
    public static ContributorsFragment newInstance(boolean useCachedContributors) {
        ContributorsFragment fragment = new ContributorsFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_USE_CACHED_CONTRIBUTORS, useCachedContributors);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contributorService = ((ContributorsApplication) getActivity().getApplicationContext()).getContributorService();
        contributorService.addListener(this);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contributors, container, false);

        //Butterknife, come help me!
        contributorsListView = (ListView) rootView.findViewById(R.id.contributors_list);
        contributorsAdapter = new ContributorsAdapter(getActivity(), new GlideImageLoader());
        contributorsListView.setAdapter(contributorsAdapter);

        contributorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        if (getArguments() != null && getArguments().getBoolean(KEY_USE_CACHED_CONTRIBUTORS)) {
            contributorService.requestCachedContributors();
        }

        return rootView;
    }


    @Override
    public void onContributorsLoaded(List<Contributor> contributors) {
        this.contributors = contributors;
        contributorsAdapter.setContributorListAndNotify(contributors);
    }

    @Override
    public void onContributorsLoadFailed(String message) {
    }

    @Override
    public void onDestroy() {
        contributorService.removeListener(this);
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_fresco) {
            //Create a new adapter
            contributorsAdapter = new ContributorsAdapter(getActivity(), contributors, new FrescoImageLoader());
            contributorsListView.setAdapter(contributorsAdapter);
            return true;
        } else if (item.getItemId() == R.id.menu_item_glide) {
            contributorsAdapter.setImageLoaderAndNotify(new GlideImageLoader());
            return true;
        } else if (item.getItemId() == R.id.menu_item_picasso) {
            contributorsAdapter.setImageLoaderAndNotify(new PicassoImageLoader());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
