package com.zack.gojektestjava;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class TrendingViewModel extends ViewModel {
    private TrendingRepository repository;

    private MediatorLiveData<List<RepoModel>> modelLiveData;

    private MutableLiveData<Boolean> errorLiveData;

    private MutableLiveData<Boolean> loadingLiveData;

    public TrendingViewModel() {
        repository = TrendingRepository.getInstance();
    }

    public LiveData<Boolean> getLoadingLiveData() {
        loadingLiveData = new MutableLiveData<>();
        loadingLiveData.setValue(true);
        return loadingLiveData;
    }

    public LiveData<Boolean> getErrorLiveData() {
        errorLiveData = new MutableLiveData<>();
        errorLiveData.setValue(true);
        return errorLiveData;
    }

    public void refetchData() {
        errorLiveData.setValue(false);
        loadingLiveData.setValue(true);
        repository.refreshData();
    }

    public LiveData<List<RepoModel>> getModelLiveData() {
        modelLiveData = new MediatorLiveData<>();
        modelLiveData.addSource(repository.getAllTrending(), new Observer<Response<List<RepoModel>>>() {
            @Override
            public void onChanged(Response<List<RepoModel>> listResponse) {
                loadingLiveData.setValue(false);
                switch (listResponse.getStatus()) {
                    case SUCCESS:
                        errorLiveData.setValue(false);
                        modelLiveData.setValue(listResponse.getData());
                        break;
                    case ERROR:
                        errorLiveData.setValue(true);
                        break;
                }
            }
        });

        return modelLiveData;
    }
}
