package zeroturnaround.org.jrebel4androidgettingstarted;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by shelajev on 16/12/15.
 */
interface GitHubService {
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> repoContributors(
            @Path("owner") String owner,
            @Path("repo") String repo);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}


