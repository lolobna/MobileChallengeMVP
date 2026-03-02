package com.example.mobilechallengemvp.ui.TrendRepos;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.mobilechallengemvp.data.model.Repo;
import com.example.mobilechallengemvp.data.repository.RepoCallback;
import com.example.mobilechallengemvp.data.repository.RepoRepository;
import java.util.List;
import javax.inject.Inject;
public class RepoPresenter implements RepoContract.Presenter {
    private RepoContract.View view;
    private RepoRepository repository;
    @Inject
    public RepoPresenter(RepoRepository repository) {
        this.repository = repository;
    }
    public void attach(RepoContract.View view) {
        this.view = view;
    }

    //------------------------------------- les méthodes à définir ----------------------------------------//
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void loadRepos(int page) {
        repository.getTrendingRepos(page, new RepoCallback() {
            @Override
            public void onSuccess(List<Repo> repos) { if (view != null) view.showRepos(repos);}
            @Override
            public void onError(Throwable t) { if (view != null) view.showError(t.getMessage());}
        });
    }
    @Override
    public void detach() {
        view = null;
    }
}