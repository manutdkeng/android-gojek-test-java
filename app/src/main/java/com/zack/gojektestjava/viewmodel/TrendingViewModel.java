package com.zack.gojektestjava.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zack.gojektestjava.model.RepoModel;
import com.zack.gojektestjava.repository.TrendingRepository;

import java.util.List;

public class TrendingViewModel extends ViewModel {
    private TrendingRepository repository;

    private MediatorLiveData<List<RepoModel>> modelLiveData;

    private MutableLiveData<Boolean> errorLiveData;

    private MutableLiveData<Boolean> loadingLiveData;

    public TrendingViewModel(TrendingRepository repository) {
        this.repository = repository;

        loadingLiveData = new MutableLiveData<>();
        loadingLiveData.setValue(true);

        errorLiveData = new MutableLiveData<>();
        errorLiveData.setValue(false);

    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<Boolean> getErrorLiveData() {
        return errorLiveData;
    }

    public void retry() {
        loadingLiveData.setValue(true);
        repository.refreshData();
    }

    public void refreshData() {
        repository.refreshData();
    }

    public LiveData<List<RepoModel>> getModelLiveData() {
        modelLiveData = new MediatorLiveData<>();
        modelLiveData.addSource(repository.getAllTrending(), listResponse -> {
            loadingLiveData.setValue(false);
            switch (listResponse.getStatus()) {
                case SUCCESS:
                    errorLiveData.setValue(false);
                    modelLiveData.setValue(listResponse.getData());
                    break;
                case ERROR:
                    errorLiveData.setValue(true);
                    modelLiveData.setValue(null);
                    break;
            }
        });
        return modelLiveData;
    }
}
