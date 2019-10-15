package com.zack.gojektestjava.adapter;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zack.gojektestjava.model.RepoModel;
import com.zack.gojektestjava.databinding.ViewRepoListItemBinding;
import com.zack.gojektestjava.util.CircleTransform;

public class RepoViewHolder extends RecyclerView.ViewHolder {
    private ViewRepoListItemBinding binding;

    public RepoViewHolder(ViewRepoListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindData(RepoModel model) {
        binding.setRepoModel(model);
        if (!TextUtils.isEmpty(model.getLanguageColor())) {
            Drawable[] drawables = binding.language.getCompoundDrawablesRelative();
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        drawable.setColorFilter(new BlendModeColorFilter(Color.parseColor(model.getLanguageColor()), BlendMode.SRC_ATOP));
                    } else {
                        drawable.setColorFilter(Color.parseColor(model.getLanguageColor()), PorterDuff.Mode.SRC_ATOP);
                    }
                }
            }
        }
        binding.language.setVisibility(TextUtils.isEmpty(model.getLanguage()) ? View.GONE : View.VISIBLE);
        Picasso.get().load(model.getAvatar()).transform(new CircleTransform()).into(binding.avatar);
    }
}
