package com.hyjoy.http.loader;


import com.hyjoy.http.data.Response;

import io.reactivex.Single;

/**
 * Service的网络方法
 * Created by hyjoy on 2018/4/19.
 */
public interface SingleDataLoader<T> {
    /**
     * Service的网络方法
     *
     * @return Single
     */
    Single<Response<T>> getLoader();
}
