package com.zack.gojektestjava.module;

import com.zack.gojektestjava.database.RepoDatabase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class,
        DatabaseModule.class})
public interface ApplicationComponent {

    @Named("Database")
    RepoDatabase getDatabase();

}
