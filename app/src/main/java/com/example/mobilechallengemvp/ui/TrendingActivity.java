package com.example.mobilechallengemvp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.mobilechallengemvp.databinding.TrendingMainBinding;
import com.example.mobilechallengemvp.ui.TrendRepos.RepoFragment;
import com.example.mobilechallengemvp.ui.TrendRepos.RepoPresenter;

public class TrendingActivity extends AppCompatActivity {
    public RepoPresenter presenter;
    private TrendingMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = TrendingMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            RepoFragment fragment = new RepoFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(binding.container.getId(), fragment, "repo_list_fragment");
            ft.commit();
        }
    }
}