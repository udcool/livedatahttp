package com.hyjoy.http.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hyjoy.http.Status;
import com.hyjoy.http.data.Response;
import com.hyjoy.http.exception.CallDataNullException;
import com.hyjoy.http.livedata.NetStatusLiveData;
import com.hyjoy.http.loader.CallDataLoader;
import com.hyjoy.http.util.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by hyjoy on 2018/4/19.
 */
public class CallDataLoadRepository<T> implements DataLoadRepository<T> {
    private MutableLiveData<T> tLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> nLiveData = new MutableLiveData<>();
    private CallDataLoader<T> callDataLoader;
    private NetStatusLiveData rLiveData = new NetStatusLiveData() {
        @Override
        protected void dispose() {
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
        }
    };

    public CallDataLoadRepository(CallDataLoader<T> callDataLoader) {
        this.callDataLoader = callDataLoader;
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
    public LiveData<Status> getStatus() {
        return rLiveData;
    }

    private Call<Response<T>> call;

    @Override
    public void loadData(boolean reLoad) {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
        call = (Call<Response<T>>) callDataLoader.getLoader();
        call.enqueue(new Callback<Response<T>>() {
            @Override
            public void onResponse(Call<Response<T>> call, retrofit2.Response<Response<T>> response) {
                if (response.isSuccessful()) {
                    Response<T> tResponse = response.body();
                    if (tResponse != null) {
                        if (tResponse.success()) {
                            rLiveData.postSuccess();
                            tLiveData.postValue(tResponse.data());
                        } else {
                            rLiveData.postError(tResponse.message());
                        }
                    } else {
                        rLiveData.postError(ErrorUtils.getErrorMessage(new CallDataNullException()));
                    }
                } else {
                    rLiveData.postError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Response<T>> call, Throwable t) {
                rLiveData.postError(ErrorUtils.getErrorMessage(t));
            }
        });
    }
}
