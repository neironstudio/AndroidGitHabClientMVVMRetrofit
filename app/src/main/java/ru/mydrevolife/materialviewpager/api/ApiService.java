package ru.mydrevolife.materialviewpager.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.mydrevolife.materialviewpager.db.RepoModel;

/**
 * Created by Den on 11.12.2017.
 */

public interface ApiService {


   @GET("/user/repos")
    Call<List<Repository>> getRepoService(
    );
    //@GET("/repos/{user}/{repo}/commits")
    //Observable<List<CommitResponse>> commits(@Path("user") String user, @Path("repo") String repo);

   // @GET("/user/repos")
   //https://github.com/neironstudio
   /* @GET("private/api/v2/json/accounts/current/")
    Call<Statuses> getStatuses();*/
}
