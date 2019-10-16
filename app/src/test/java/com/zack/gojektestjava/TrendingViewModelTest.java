package com.zack.gojektestjava;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.zack.gojektestjava.model.RepoModel;
import com.zack.gojektestjava.repository.TrendingRepository;
import com.zack.gojektestjava.util.Response;
import com.zack.gojektestjava.viewmodel.TrendingViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TrendingViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule
            = new InstantTaskExecutorRule();

    @Mock
    TrendingRepository repository;

    @Mock
    Observer<List<RepoModel>> dataObserver;

    private TrendingViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        viewModel = new TrendingViewModel(repository);
    }

    @Test
    public void testFetchData_returnNull() {
        MediatorLiveData<Response<List<RepoModel>>> liveData = new MediatorLiveData<>();
        when(repository.getAllTrending()).thenReturn(liveData);

        viewModel.getLoadingLiveData();
        viewModel.getErrorLiveData();
        viewModel.getModelLiveData().observeForever(dataObserver);

        assertEquals(true, viewModel.getLoadingLiveData().getValue());
        assertEquals(false, viewModel.getErrorLiveData().getValue());
        liveData.setValue(Response.success(null));
        assertEquals(false, viewModel.getErrorLiveData().getValue());
        assertEquals(false, viewModel.getLoadingLiveData().getValue());
        verify(dataObserver).onChanged(null);
    }

    @Test
    public void testFetchData_returnEmptyArray() {
        MediatorLiveData<Response<List<RepoModel>>> liveData = new MediatorLiveData<>();
        when(repository.getAllTrending()).thenReturn(liveData);
        List<RepoModel> models = new ArrayList<>();

        viewModel.getLoadingLiveData();
        viewModel.getErrorLiveData();
        viewModel.getModelLiveData().observeForever(dataObserver);

        assertEquals(true, viewModel.getLoadingLiveData().getValue());
        assertEquals(false, viewModel.getErrorLiveData().getValue());
        liveData.setValue(Response.success(models));
        assertEquals(false, viewModel.getErrorLiveData().getValue());
        assertEquals(false, viewModel.getLoadingLiveData().getValue());
        verify(dataObserver).onChanged(models);
    }

    @Test
    public void testFetchData_returnSomeData() {
        MediatorLiveData<Response<List<RepoModel>>> liveData = new MediatorLiveData<>();
        when(repository.getAllTrending()).thenReturn(liveData);
        List<RepoModel> models = new ArrayList<>();
        models.add(new RepoModel());
        models.add(new RepoModel());

        viewModel.getLoadingLiveData();
        viewModel.getErrorLiveData();
        viewModel.getModelLiveData().observeForever(dataObserver);

        assertEquals(true, viewModel.getLoadingLiveData().getValue());
        assertEquals(false, viewModel.getErrorLiveData().getValue());
        liveData.setValue(Response.success(models));
        assertEquals(false, viewModel.getErrorLiveData().getValue());
        assertEquals(false, viewModel.getLoadingLiveData().getValue());
        verify(dataObserver).onChanged(models);
    }

    @Test
    public void testFetchData_returnError() {
        MediatorLiveData<Response<List<RepoModel>>> liveData = new MediatorLiveData<>();
        when(repository.getAllTrending()).thenReturn(liveData);

        viewModel.getLoadingLiveData();
        viewModel.getErrorLiveData();
        viewModel.getModelLiveData().observeForever(dataObserver);

        assertEquals(true, viewModel.getLoadingLiveData().getValue());
        assertEquals(false, viewModel.getErrorLiveData().getValue());
        liveData.setValue(Response.error("Empty response"));
        assertEquals(true, viewModel.getErrorLiveData().getValue());
        assertEquals(false, viewModel.getLoadingLiveData().getValue());
        verify(dataObserver).onChanged(null);
    }

    @Test
    public void testRetry_afterFetchDataError() {
        MediatorLiveData<Response<List<RepoModel>>> liveData = new MediatorLiveData<>();
        when(repository.getAllTrending()).thenReturn(liveData);

        viewModel.getLoadingLiveData();
        viewModel.getModelLiveData().observeForever(dataObserver);

        liveData.setValue(Response.error("Empty response"));
        assertEquals(false, viewModel.getLoadingLiveData().getValue());
        viewModel.retry();
        assertEquals(true, viewModel.getLoadingLiveData().getValue());
        verify(repository, times(1)).refreshData();
    }

    @Test
    public void testRefreshData() {
        viewModel.retry();
        verify(repository, times(1)).refreshData();
    }

    @After
    public void tearDown() {
        viewModel = null;
        repository = null;
    }
}
