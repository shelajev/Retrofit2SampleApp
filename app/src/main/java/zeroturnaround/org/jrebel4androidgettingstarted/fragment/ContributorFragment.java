package zeroturnaround.org.jrebel4androidgettingstarted.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import org.parceler.transfuse.annotations.Bind;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zeroturnaround.org.jrebel4androidgettingstarted.ContributorsApplication;
import zeroturnaround.org.jrebel4androidgettingstarted.R;
import zeroturnaround.org.jrebel4androidgettingstarted.imageloader.impl.GlideImageLoader;
import zeroturnaround.org.jrebel4androidgettingstarted.service.Contributor;
import zeroturnaround.org.jrebel4androidgettingstarted.service.ContributorsService;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContributorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContributorFragment extends Fragment implements ContributorsService.ContributorsListener {

    @InjectExtra Contributor contributor;

    @BindView(R.id.textview_bio) TextView bio_textview;
    @BindView(R.id.textview_company) TextView company_textview;
    @BindView(R.id.textview_email) TextView email_textview;
    @BindView(R.id.textview_name) TextView name_textview;
    @BindView(R.id.contributor_avatar) ImageView avatar_imageview;

    private ContributorsService contributorService;


    public ContributorFragment() {
        // Required empty public constructor
    }


    public static ContributorFragment newInstance() {
        ContributorFragment fragment = new ContributorFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contributorService = ((ContributorsApplication) getActivity().getApplicationContext()).getContributorService();
        contributorService.addListener(this);
        Dart.inject(this, getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contributor, container, false);

        ButterKnife.bind(this, rootView);
        onContributorLoaded(contributor);
        contributorService.requestUser(contributor);

        GlideImageLoader glideImageLoader = new GlideImageLoader();
        glideImageLoader.loadImage(contributor.getAvatarUrl(), avatar_imageview);

        return  rootView;
    }

    @Override
    public void onContributorsLoaded(List<Contributor> contributors) {

    }

    @Override
    public void onContributorsLoadFailed(String message) {

    }

    @Override
    public void onContributorLoaded(Contributor contributor) {
        bio_textview.setText(contributor.getBio());
        email_textview.setText(contributor.getEmail());
        company_textview.setText(contributor.getCompany());
        name_textview.setText(contributor.getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contributorService.removeListener(this);
    }
}
