package com.hyjoy.livedatahttp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Gank Api
 * Created by hyjoy on 2018/3/6.
 */
public class GankApi {
    private static final Map<Class, Object> sServices = new HashMap<>();


    private GankApi() {

    }

    private static final OkHttpClient sClient;
    private static final Retrofit sRetrofit;

    static {
        sClient = new OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        sRetrofit = new Retrofit.Builder()
                .client(sClient)
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    /**
     * 创建相应的api
     *
     * @param clazz :
     * @param <T>   :
     * @return :
     */
    public static <T> T getService(Class<T> clazz) {
        Object obj = sServices.get(clazz);
        if (obj != null) {
            return (T) obj;
        }
        T t = sRetrofit.create(clazz);
        sServices.put(clazz, t);
        return t;
    }
}
