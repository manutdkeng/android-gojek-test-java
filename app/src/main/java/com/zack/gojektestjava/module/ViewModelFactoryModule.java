package com.zack.gojektestjava.module;

import com.zack.gojektestjava.repository.TrendingRepository;
import com.zack.gojektestjava.viewmodel.TrendingViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelFactoryModule {

    @Provides
    public TrendingViewModelFactory provideTrendingViewModelFactory(TrendingRepository repository) {
        return new TrendingViewModelFactory(repository);
    }
}
