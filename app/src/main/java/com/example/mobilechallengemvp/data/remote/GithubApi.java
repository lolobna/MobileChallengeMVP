package com.example.mobilechallengemvp.data.remote;

import com.example.mobilechallengemvp.data.model.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubApi {

    @GET("search/repositories")
    Call<SearchResponse> getTrendingRepos(
            @Query("q") String created,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") int page
    );
}

