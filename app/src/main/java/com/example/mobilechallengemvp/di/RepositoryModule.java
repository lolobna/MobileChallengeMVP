package com.example.mobilechallengemvp.di;
import com.example.mobilechallengemvp.data.remote.GithubApi;
import com.example.mobilechallengemvp.data.repository.RepoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class RepositoryModule {
    @Provides
    @Singleton
    RepoRepository provideRepoRepository(GithubApi api) {
        return new RepoRepository(api);
    }
}