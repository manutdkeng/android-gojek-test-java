package com.zack.gojektestjava;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zack.gojektestjava.databinding.ViewRepoListItemBinding;

public class RepoViewHolder extends RecyclerView.ViewHolder {
    private ViewRepoListItemBinding binding;
    public RepoViewHolder(ViewRepoListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindData(RepoModel model) {
        binding.setRepoModel(model);
        binding.language.setVisibility(TextUtils.isEmpty(model.getLanguage()) ? View.GONE : View.VISIBLE);
        Picasso.get().load(model.getAvatar()).transform(new CircleTransform()).into(binding.avatar);
    }
}
