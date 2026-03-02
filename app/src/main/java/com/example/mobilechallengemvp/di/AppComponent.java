package com.example.mobilechallengemvp.di;
import com.example.mobilechallengemvp.ui.TrendRepos.RepoFragment;
import com.example.mobilechallengemvp.ui.TrendingActivity;
import javax.inject.Singleton;
import dagger.Component;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(RepoFragment fragment); // pour le fragment
}