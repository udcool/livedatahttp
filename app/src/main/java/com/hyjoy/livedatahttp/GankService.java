package com.hyjoy.livedatahttp;

import com.hyjoy.livedatahttp.entity.BaseListData;
import com.hyjoy.livedatahttp.entity.BaseResponse;
import com.hyjoy.livedatahttp.entity.Gank;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hyjoy on 2018/4/19.
 */
public interface GankService {
    @GET("data/Android/{pageCnt}/{pageNo}")
    Single<BaseResponse<BaseListData<Gank>>> androids(@Path("pageCnt") int pageCnt, @Path("pageNo") int pageNo);
}
