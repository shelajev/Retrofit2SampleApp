package zeroturnaround.org.jrebel4androidgettingstarted.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import zeroturnaround.org.jrebel4androidgettingstarted.ContributorsApplication;
import zeroturnaround.org.jrebel4androidgettingstarted.R;
import zeroturnaround.org.jrebel4androidgettingstarted.service.Contributor;
import zeroturnaround.org.jrebel4androidgettingstarted.service.ContributorsService;

public class SearchFragment extends Fragment implements ContributorsService.ContributorsListener {

    private EditText companyEditText;
    private EditText repositoryEditText;
    private Button searchButton;
    private View inputContent;
    private ContributorsService contributorsService;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contributorsService = ((ContributorsApplication) getActivity().getApplicationContext()).getContributorService();
        contributorsService.addListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        companyEditText = (EditText) rootView.findViewById(R.id.company_edittext);
        repositoryEditText = (EditText) rootView.findViewById(R.id.repository_edittext);
        searchButton = (Button) rootView.findViewById(R.id.search_button);
        inputContent = rootView.findViewById(R.id.input_content);
        inputContent.post(new Runnable() {
            @Override
            public void run() {
                inputContent.requestFocus();
            }
        });

        final FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companySearchKeyword = companyEditText.getEditableText().toString();
                String repositorySearchKeyword = repositoryEditText.getEditableText().toString();
                contributorsService.requestContributors(companySearchKeyword, repositorySearchKeyword);

                sendSearchAnalytics(companySearchKeyword, repositorySearchKeyword, firebaseAnalytics);
            }
        });
        return rootView;
    }

    private void sendSearchAnalytics(String companySearchKeyword, String repositorySearchKeyword, FirebaseAnalytics firebaseAnalytics) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, repositorySearchKeyword + "&" + companySearchKeyword);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
    }

    @Override
    public void onContributorsLoaded(List<Contributor> contributors) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(ContributorsFragment.class.getName()) == null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().
                    beginTransaction().add(R.id.fragment_holder, ContributorsFragment.newInstance(true), ContributorsFragment.class.getName());
            transaction.addToBackStack(ContributorsFragment.class.getName());
            transaction.commit();
        }
    }


    @Override
    public void onContributorsLoadFailed(String message) {
        //TODO show failure message!
    }

    @Override
    public void onDestroy() {
        contributorsService.removeListener(this);
        super.onDestroy();
    }
}
