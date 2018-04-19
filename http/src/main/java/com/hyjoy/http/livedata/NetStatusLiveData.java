package com.hyjoy.http.livedata;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import com.hyjoy.http.Status;

/**
 * Created by hyjoy on 2018/4/19.
 */
public abstract class NetStatusLiveData extends MediatorLiveData<Status> {
    public void postError(String error) {
        postValue(Status.error(error));
    }

    public void postLoading() {
        postValue(Status.loading());
    }

    public void postSuccess() {
        postValue(Status.success());
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<Status> observer) {
        super.observe(owner, status -> {
            observer.onChanged(status);
            if (status == null || status.isError() || status.isSuccess()) {
                postValue(Status.nul());
            }
        });
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (!hasObservers()) {
            dispose();
        }
    }

    protected abstract void dispose();
}
