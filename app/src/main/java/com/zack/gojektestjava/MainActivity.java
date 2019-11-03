package com.zack.gojektestjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.zack.gojektestjava.adapter.TrendingRecyclerViewAdapter;
import com.zack.gojektestjava.databinding.ActivityMainBinding;
import com.zack.gojektestjava.repository.TrendingRepository;
import com.zack.gojektestjava.viewmodel.TrendingViewModel;
import com.zack.gojektestjava.viewmodel.TrendingViewModelFactory;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TrendingViewModel viewModel;
    private TrendingRecyclerViewAdapter adapter;

//    @Inject
//    public TrendingViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this, MyApplication.getInstance().component.getTrendingViewModelFactory()).get(TrendingViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        setTitle(R.string.title);

        setObservers();
        setListener();
    }

    private void setObservers() {
        viewModel.getLoadingLiveData().observe(this, loadingState -> {
            Log.d("MainActivity", "Loading state changed: " + loadingState);
            binding.loadingLayout.setVisibility(loadingState ? View.VISIBLE : View.GONE);
        });

        viewModel.getErrorLiveData().observe(this, showError -> {
            Log.d("MainActivity", "Error state changed: " + showError);
            binding.swipeContainer.setRefreshing(false);
            binding.errorLayout.setVisibility(showError ? View.VISIBLE : View.GONE);
            binding.swipeContainer.setVisibility(showError ? View.GONE : View.VISIBLE);
        });

        viewModel.getModelLiveData().observe(this, repoModels -> {
            binding.swipeContainer.setRefreshing(false);
            if (adapter == null) {
                adapter = new TrendingRecyclerViewAdapter(repoModels);
                binding.trendingList.setAdapter(adapter);
            } else {
                adapter.updateData(repoModels);
            }
        });
    }

    private void setListener() {
        binding.swipeContainer.setOnRefreshListener(() -> viewModel.refreshData());
    }
}
