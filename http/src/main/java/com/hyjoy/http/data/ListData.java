package com.hyjoy.http.data;

import java.util.List;

/**
 * Created by hyjoy on 2018/4/19.
 */
public interface ListData<T> {
    List<T> data();

    int pageNo();

    int count();
}
