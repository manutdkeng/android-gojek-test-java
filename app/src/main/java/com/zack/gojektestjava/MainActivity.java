package com.zack.gojektestjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.zack.gojektestjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(R.layout.activity_main);

        TrendingViewModel viewModel = ViewModelProviders.of(this).get(TrendingViewModel.class);
        binding.setLifecycleOwner(this);

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
            }
        });
    }
}
