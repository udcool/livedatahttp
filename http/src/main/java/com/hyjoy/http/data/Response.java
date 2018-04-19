package com.hyjoy.http.data;

/**
 * Created by hyjoy on 2018/4/19.
 */
public interface Response<T> {

    /**
     * 状态码
     *
     * @return
     */
    int code();

    /**
     * 消息
     *
     * @return
     */
    String message();

    /**
     * 数据体
     *
     * @return
     */
    T data();

    boolean success();
}
