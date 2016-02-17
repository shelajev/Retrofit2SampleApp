package zeroturnaround.org.jrebel4androidgettingstarted.service;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sten on 17/02/16.
 */
public class ContributorsService {

    private ArrayList<ContributorsListener> listeners = new ArrayList<>();
    final GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
    private List<Contributor> contributors = new ArrayList<>();

    public void addListener(ContributorsListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ContributorsListener listener) {
        listeners.remove(listener);
    }

    public void requestContributors(String company, String repository) {
        if (TextUtils.isEmpty(company) || TextUtils.isEmpty(repository)) {
            for (ContributorsListener listener : listeners) {
                listener.onContributorsLoadFailed("Invalid arguments");
            }
            return;
        }
        final Call<List<Contributor>> call = gitHubService.repoContributors(company, repository);
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                if (response.isSuccess()) {
                    contributors = response.body();
                    for (ContributorsListener listener : listeners) {
                        listener.onContributorsLoaded(contributors);
                    }
                } else {
                    for (ContributorsListener listener : listeners) {
                        listener.onContributorsLoadFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                for (ContributorsListener listener : listeners) {
                    listener.onContributorsLoadFailed(t.getMessage());
                }
            }

        });
    }

    public void requestCachedContributors() {
        for (ContributorsListener listener : listeners) {
            listener.onContributorsLoaded(contributors);
        }

    }

    public interface ContributorsListener {

        void onContributorsLoaded(List<Contributor> contributors);
        void onContributorsLoadFailed(String message);
    }
}
