package com.example.mobilechallengemvp.ui;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import com.example.mobilechallengemvp.R;
import com.example.mobilechallengemvp.ui.TrendRepos.RepoFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class TrendingActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending_main); // layout avec FrameLayout id="container"

        // Ajouter le fragment seulement si c'est la première création
        if (savedInstanceState == null) {
            RepoFragment fragment = new RepoFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.container, fragment, "repo_list_fragment");
            ft.commit();
        }
    }
}