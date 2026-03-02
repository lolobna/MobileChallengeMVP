package com.example.mobilechallengemvp.di;
import com.example.mobilechallengemvp.data.remote.GithubApi;
import com.example.mobilechallengemvp.data.remote.RetrofitClient;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    GithubApi provideGithubApi() {
        return RetrofitClient.getInstance().create(GithubApi.class);
    }
}