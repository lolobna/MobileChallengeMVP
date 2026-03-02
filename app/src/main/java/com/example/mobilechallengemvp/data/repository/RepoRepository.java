package com.example.mobilechallengemvp.data.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.mobilechallengemvp.data.remote.GithubApi;
import com.example.mobilechallengemvp.data.model.SearchResponse;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoRepository {
    private final GithubApi api;
    @Inject
    public RepoRepository(GithubApi api) {
        this.api = api;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getTrendingRepos(int page, RepoCallback callback) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30); // derniers 30 jours
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(cal.getTime());
        String query = "created:>" + date;

        api.getTrendingRepos(query, "stars", "desc", page)
                .enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body().getItems());
                        } else {
                            callback.onError(new Exception("API error"));
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }
}