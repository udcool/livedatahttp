package com.hyjoy.livedatahttp

import com.hyjoy.livedatahttp.entity.BaseListData
import com.hyjoy.livedatahttp.entity.BaseResponse
import com.hyjoy.livedatahttp.entity.Gank
import com.laidian360.tuodian.base.BaseListViewModel
import io.reactivex.Single

/**
 * Created by hyjoy on 2018/4/19.
 */
class PageViewModel : BaseListViewModel<Gank>() {
    override fun dataService(page: Int, rp: Int): Single<BaseResponse<BaseListData<Gank>>> {
        return GankApi.getService(GankService::class.java).androids(rp, page)
    }
}