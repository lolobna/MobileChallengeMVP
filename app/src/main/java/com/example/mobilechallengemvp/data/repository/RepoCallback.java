package com.example.mobilechallengemvp.data.repository;

import com.example.mobilechallengemvp.data.model.Repo;

import java.util.List;

public interface RepoCallback {
    void onSuccess(List<Repo> repos);
    void onError(Throwable t);
}
