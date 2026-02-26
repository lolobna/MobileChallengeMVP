package com.example.mobilechallengemvp.ui.TrendRepos;

import com.example.mobilechallengemvp.data.model.Repo;

import java.util.List;

public interface RepoContract {

    interface View {
        void showRepos(List<Repo> repos);
        void showError(String message);
    }

    interface Presenter {
        void loadRepos(int page);
        void detach();
    }
}