package com.example.mobilechallengemvp.ui.TrendRepos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilechallengemvp.R;
import com.example.mobilechallengemvp.data.model.Repo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM = 0;
    private final int LOADING = 1;

    private List<Repo> repos = new ArrayList<>();
    private boolean isLoadingAdded = false;

    // ------------------- Adapter methods -------------------

    @Override
    public int getItemViewType(int position) {
        return (position == repos.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_repo, parent, false);
            return new RepoVH(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM) {
            Repo repo = repos.get(position);
            RepoVH repoHolder = (RepoVH) holder;
            repoHolder.name.setText(repo.getName());
            repoHolder.desc.setText(repo.getDescription());
            repoHolder.stars.setText("⭐ " + repo.getStargazersCount());
            repoHolder.user.setText(repo.getOwner().getUsername());
            Picasso.get().load(repo.getOwner().getAvatar()).into(repoHolder.avatar);
        }
        // Footer loading : rien à binder
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    // ------------------- Public helper methods -------------------

    public void addRepos(List<Repo> newRepos) {
        int start = repos.size();
        repos.addAll(newRepos);
        notifyItemRangeInserted(start, newRepos.size());
    }

    public void addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true;
            repos.add(new Repo()); // item vide pour footer
            notifyItemInserted(repos.size() - 1);
        }
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded && repos.size() > 0) {
            isLoadingAdded = false;
            int position = repos.size() - 1;
            repos.remove(position);
            notifyItemRemoved(position);
        }
    }

    // ------------------- ViewHolders -------------------

    static class RepoVH extends RecyclerView.ViewHolder {
        TextView name, desc, stars, user;
        ImageView avatar;

        RepoVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.repoName);
            desc = itemView.findViewById(R.id.repoDescription);
            stars = itemView.findViewById(R.id.repoStars);
            user = itemView.findViewById(R.id.repoOwner);
            avatar = itemView.findViewById(R.id.ownerAvatar);
        }
    }

    static class LoadingVH extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        LoadingVH(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}