package com.zack.gojektestjava.module;

import com.zack.gojektestjava.MainActivity;
import com.zack.gojektestjava.database.RepoDatabase;
import com.zack.gojektestjava.viewmodel.TrendingViewModelFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class,
        DatabaseModule.class,
        ViewModelFactoryModule.class})
public interface ApplicationComponent {

    @Named("Database")
    RepoDatabase getDatabase();

    TrendingViewModelFactory getTrendingViewModelFactory();

    void inject(MainActivity activity);

}
