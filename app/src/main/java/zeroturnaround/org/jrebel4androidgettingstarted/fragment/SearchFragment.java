package zeroturnaround.org.jrebel4androidgettingstarted.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.f2prateek.dart.Dart;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;
import zeroturnaround.org.jrebel4androidgettingstarted.Henson;
import zeroturnaround.org.jrebel4androidgettingstarted.R;


public class SearchFragment extends Fragment{


    @BindView(R.id.company_edittext) EditText companyEditText;
    @BindView(R.id.repository_edittext) EditText repositoryEditText;
    @State String lastSearchCombination;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dart.inject(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);
        Icepick.restoreInstanceState(this, savedInstanceState);
        return rootView;
    }


    @OnClick(R.id.search_button)
    public void searchContributors() {
        String companySearchKeyword = companyEditText.getEditableText().toString();
        String repositorySearchKeyword = repositoryEditText.getEditableText().toString();
        lastSearchCombination = companySearchKeyword + " " + repositorySearchKeyword;

        Intent intentContributorsFragment = Henson.with(getActivity()).gotoContributorsFragment().companyQuery(companySearchKeyword).repositoryQuery(repositorySearchKeyword).build();
        Intent intentContributorsActivity = Henson.with(getActivity()).gotoContributorsActivity().build();
        intentContributorsActivity.putExtras(intentContributorsFragment);
        startActivity(intentContributorsActivity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
