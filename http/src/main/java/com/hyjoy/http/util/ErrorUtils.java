package com.hyjoy.http.util;

import com.google.gson.JsonParseException;
import com.hyjoy.http.BuildConfig;
import com.hyjoy.http.exception.ServerException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import retrofit2.HttpException;

/**
 * Created by hyjoy on 2018/4/19.
 */
public class ErrorUtils {

    public static String getErrorMessage(Throwable e) {
        ServerException serverException = new ServerException(e);
        if (e instanceof ConnectException
                || e instanceof UnknownHostException
                || e instanceof UnknownServiceException
                || e instanceof HttpException) {
            serverException.setShowMessage("网络已断开，请稍后连接网络。");
        } else if (e instanceof SocketTimeoutException) {
            serverException.setShowMessage("请求超时, 请稍后再试。");
        } else if (e instanceof JsonParseException) {
            serverException.setShowMessage("数据异常，请联系管理人员。");
        } else if (e instanceof ServerException) {
            serverException = (ServerException) e;
        } else {
            serverException.setShowMessage("请求失败, 请稍后再试。");
        }
        return BuildConfig.DEBUG ? serverException.getMessage() : serverException.getShowMessage();
    }


//    public static <T> ObservableField<T> getDataBingField(LiveData<T> liveData) {
//        ObservableField<T> observableField = new ObservableField<>();
//        liveData.observeForever(observableField::set);
//        return observableField;
//    }
}
