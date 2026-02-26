package com.example.mobilechallengemvp.ui.TrendRepos;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilechallengemvp.MyApp;
import com.example.mobilechallengemvp.R;
import com.example.mobilechallengemvp.data.model.Repo;

import java.util.List;

import javax.inject.Inject;

public class RepoFragment extends Fragment implements RepoContract.View {

    @Inject
    RepoPresenter presenter;
    private RecyclerView recyclerView;
    private RepoAdapter adapter;
    private ImageButton btnScrollTop;

    private int currentPage = 1;
    private boolean isLoading = false;

    public RepoFragment() { }

    public static RepoFragment newInstance() {
        return new RepoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Injection Dagger2
        ((MyApp) requireActivity().getApplication())
                .getAppComponent()
                .inject(this);

        presenter.attach(this);

        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new RepoAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        btnScrollTop = view.findViewById(R.id.btnScrollTop);
        // Scroll listener pour pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();

                if (layoutManager != null) {

                    //  afficher bouton si pas en haut
                    if (layoutManager.findFirstVisibleItemPosition() > 3) {
                        btnScrollTop.setVisibility(View.VISIBLE);
                    } else {
                        btnScrollTop.setVisibility(View.GONE);
                    }

                    //  pagination
                    if (!isLoading) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            loadPage();
                        }
                    }
                }
            }
        });
        btnScrollTop.setOnClickListener(v ->
                recyclerView.smoothScrollToPosition(0)
        );

        // Charger la première page
        loadPage();
    }

    private void loadPage() {
        isLoading = true;
        adapter.addLoadingFooter(); // ajoute le footer ProgressBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            presenter.loadRepos(currentPage);
        }
    }

    @Override
    public void showRepos(List<Repo> repos) {
        adapter.removeLoadingFooter(); // retire le footer
        adapter.addRepos(repos);       // ajoute les nouveaux repos
        isLoading = false;             // libère le flag pour scroll
        currentPage++;                 // prépare la page suivante
    }

    @Override
    public void showError(String message) {
        adapter.removeLoadingFooter();
        isLoading = false;
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}