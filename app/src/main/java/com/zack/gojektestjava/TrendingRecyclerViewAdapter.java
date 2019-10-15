package com.zack.gojektestjava;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zack.gojektestjava.databinding.ViewRepoListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TrendingRecyclerViewAdapter extends RecyclerView.Adapter<RepoViewHolder> {
    private List<RepoModel> models;

    public TrendingRecyclerViewAdapter(List<RepoModel> models) {
        this.models = new ArrayList<>();
        if (models != null) {
            this.models.addAll(models);
        }
    }

    public void updateData(List<RepoModel> newData) {
        if (newData != null) {
            this.models.clear();
            this.models.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewRepoListItemBinding binding = ViewRepoListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RepoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
