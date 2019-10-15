package com.zack.gojektestjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.zack.gojektestjava.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TrendingViewModel viewModel;
    private TrendingRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(TrendingViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        setObservers();
    }

    private void setObservers() {
        viewModel.getLoadingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loadingState) {
                binding.loadingLayout.setVisibility(loadingState ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.getErrorLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showError) {
                binding.errorLayout.setVisibility(showError ? View.VISIBLE : View.GONE);
                binding.trendingList.setVisibility(showError ? View.GONE : View.VISIBLE);
            }
        });

        viewModel.getModelLiveData().observe(this, new Observer<List<RepoModel>>() {
            @Override
            public void onChanged(List<RepoModel> repoModels) {
                if (adapter == null) {
                    adapter = new TrendingRecyclerViewAdapter(repoModels);
                    binding.trendingList.setAdapter(adapter);
                } else {
                    adapter.updateData(repoModels);
                }
            }
        });
    }
}
