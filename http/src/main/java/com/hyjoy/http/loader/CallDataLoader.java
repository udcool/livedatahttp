package com.hyjoy.http.loader;


import com.hyjoy.http.data.Response;

import retrofit2.Call;

/**
 * Service的网络方法
 * Created by hyjoy on 2018/4/19.
 */
public interface CallDataLoader<T> {
    /**
     * Service的网络方法
     *
     * @return Single
     */
    Call<? extends Response<T>> getLoader();
}
