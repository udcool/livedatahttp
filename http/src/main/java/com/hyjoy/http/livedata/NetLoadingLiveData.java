package com.hyjoy.http.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.hyjoy.http.Status;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 多个网络请求时loading的merge
 * Created by hyjoy on 2018/4/19.
 */
public class NetLoadingLiveData extends MediatorLiveData<Status> {
    private AtomicInteger index = new AtomicInteger(0);
    private Status allResource;

    public NetLoadingLiveData() {
    }

    public NetLoadingLiveData(LiveData<Status>... liveData) {
        for (LiveData<Status> lD : liveData) {
            addResourceLiveData(lD);
        }
    }

    public void addResourceLiveData(LiveData<Status> liveData) {
        addSource(liveData, resource -> {
            if (resource == null) {
                return;
            }
            if (resource.isLoading()) {
                if (index.getAndIncrement() == 0) {
                    postValue(Status.loading());
                }
            } else {
                if (index.decrementAndGet() == 0) {
                    if (allResource == null) {
                        allResource = resource;
                    } else {
                        if (allResource.isError() || resource.isError()) {
                            allResource = Status.error("");
                        }
                        removeSource(liveData);
                    }
                    postValue(allResource);
                }
            }
        });
    }
}
