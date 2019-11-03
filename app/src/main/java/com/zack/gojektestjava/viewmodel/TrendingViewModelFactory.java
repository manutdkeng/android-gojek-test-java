package com.zack.gojektestjava.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zack.gojektestjava.repository.TrendingRepository;

import javax.inject.Inject;

public class TrendingViewModelFactory implements ViewModelProvider.Factory {

    private TrendingRepository repository;

    public TrendingViewModelFactory(TrendingRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TrendingViewModel.class)) {
            return (T) new TrendingViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown viewModel class");
    }
}
