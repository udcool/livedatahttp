package com.hyjoy.http.loader;


import com.hyjoy.http.data.ListData;
import com.hyjoy.http.data.Response;

import io.reactivex.Single;

/**
 * 分页时的Service的网络方法
 * Created by hyjoy on 2018/4/19.
 */
public interface SinglePageLoader<T> {
    /**
     * 获取分页时的Service的网络方法
     *
     * @param page 当前页数
     * @param rp   每页总数
     * @return Single
     */
    Single<? extends Response<? extends ListData<T>>> getLoader(int page, int rp);
}
