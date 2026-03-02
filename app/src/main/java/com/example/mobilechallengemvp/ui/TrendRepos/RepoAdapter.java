package com.example.mobilechallengemvp.ui.TrendRepos;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobilechallengemvp.data.model.Repo;
import com.example.mobilechallengemvp.databinding.ItemLoadingBinding;
import com.example.mobilechallengemvp.databinding.ItemRepoBinding;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM = 0;
    private final int LOADING = 1;
    private List<Repo> repos = new ArrayList<>();
    private boolean isLoadingAdded = false;
    @Override
    public int getItemViewType(int position) {
        return (position == repos.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            ItemRepoBinding binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new RepoVH(binding);
        } else {
            ItemLoadingBinding binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new LoadingVH(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM) {
            Repo repo = repos.get(position);
            RepoVH repoHolder = (RepoVH) holder;
            repoHolder.binding.repoName.setText(repo.getName());
            repoHolder.binding.repoDescription.setText(repo.getDescription());
            repoHolder.binding.repoStars.setText("⭐" + repo.getStargazersCount());
            repoHolder.binding.repoOwner.setText(repo.getOwner().getUsername());
            Picasso.get().load(repo.getOwner().getAvatar()).into(repoHolder.binding.ownerAvatar);
        }
        //rien a binder pour loading
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    //------------------------------les methodes helper---------------------------//

    public void addRepos(List<Repo> newRepos) {
        int start = repos.size();
        repos.addAll(newRepos);
        notifyItemRangeInserted(start, newRepos.size());
    }

    public void addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true;
            repos.add(new Repo());
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

    //------------------------------les viewHolders---------------------------------------//
    static class RepoVH extends RecyclerView.ViewHolder {
        ItemRepoBinding binding;
        RepoVH(@NonNull ItemRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    static class LoadingVH extends RecyclerView.ViewHolder {
        ItemLoadingBinding binding;
        LoadingVH(@NonNull ItemLoadingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}