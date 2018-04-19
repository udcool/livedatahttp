package com.hyjoy.http.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hyjoy.http.Status;
import com.hyjoy.http.data.Response;
import com.hyjoy.http.livedata.NetStatusLiveData;
import com.hyjoy.http.loader.SingleDataLoader;
import com.hyjoy.http.util.ErrorUtils;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by hyjoy on 2018/4/19.
 */
public class SingleDataLoadRepository<T> implements DataLoadRepository<T> {

    private SingleDataLoader<T> singleDataLoader;

    public SingleDataLoadRepository(SingleDataLoader<T> singleDataLoader) {
        this.singleDataLoader = singleDataLoader;
    }

    private MutableLiveData<T> tLiveData = new MutableLiveData<T>();
    private NetStatusLiveData rLiveData = new NetStatusLiveData() {
        @Override
        protected void dispose() {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    };
    private MutableLiveData<Boolean> nLiveData = new MutableLiveData<>();
    private Disposable disposable;

    @Override
    public LiveData<Status> getStatus() {
        return rLiveData;
    }

    @Override
    public LiveData<T> getData() {
        return tLiveData;
    }

    @Override
    public LiveData<Boolean> noData() {
        return nLiveData;
    }

    @Override
    public void loadData(boolean reLoad) {
        if (!reLoad && rLiveData.getValue() != null && rLiveData.getValue().isLoading()) {
            return;
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        rLiveData.postLoading();
        singleDataLoader.getLoader().subscribe(new SingleObserver<Response<T>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(Response<T> tResponse) {
                if (tResponse.success()) {
                    rLiveData.postSuccess();
                    tLiveData.postValue(tResponse.data());
                } else {
                    rLiveData.postError(tResponse.message());
                }
            }

            @Override
            public void onError(Throwable e) {
                rLiveData.postError(ErrorUtils.getErrorMessage(e));
            }
        });
    }
}
