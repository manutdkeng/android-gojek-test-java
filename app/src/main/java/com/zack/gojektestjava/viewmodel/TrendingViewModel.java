package com.zack.gojektestjava.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.zack.gojektestjava.model.RepoModel;
import com.zack.gojektestjava.repository.TrendingRepository;
import com.zack.gojektestjava.util.SharePref;
import com.zack.gojektestjava.util.Utilities;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TrendingViewModel extends ViewModel {
    private TrendingRepository repository;

    private MediatorLiveData<List<RepoModel>> modelLiveData;

    private MutableLiveData<Boolean> errorLiveData;

    private MutableLiveData<Boolean> loadingLiveData;

    private MediatorLiveData<String> lastUpdatedLiveData;

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

    public LiveData<String> getLastUpdatedLiveData() {
        lastUpdatedLiveData = new MediatorLiveData<>();
        lastUpdatedLiveData.addSource(repository.getLastUpdatedLiveData(), new Observer<Long>() {
            @Override
            public void onChanged(Long timeInMillies) {
                long timeDiffInMin = Utilities.compareTime(timeInMillies, TimeUnit.MINUTES);
                if (timeDiffInMin >= 1 && timeDiffInMin < 30) {
                    lastUpdatedLiveData.setValue(String.valueOf(timeDiffInMin));
                } else if (timeDiffInMin >= 30 && timeDiffInMin < 60) {
                    lastUpdatedLiveData.setValue("30");
                } else if (timeDiffInMin >= 60 && timeDiffInMin < 120) {
                    lastUpdatedLiveData.setValue("1");
                } else {
                    lastUpdatedLiveData.setValue("");
                }
            }
        });
        return lastUpdatedLiveData;
    }

    public void retry() {
        loadingLiveData.setValue(true);
        repository.refreshData();
    }

    private void setLastUpdatedTime() {
        long lastUpdateTime = SharePref.getInstance().getLastUpdatedDate();

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
