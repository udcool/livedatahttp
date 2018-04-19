package com.laidian360.tuodian.base

import android.arch.lifecycle.ViewModel
import com.hyjoy.http.repository.SinglePagesLoadRepository
import com.hyjoy.livedatahttp.entity.BaseListData
import com.hyjoy.livedatahttp.entity.BaseResponse
import io.reactivex.Single

/**
 * 列表页面ViewModel
 * Created by hyjoy on 2018/4/19.
 */
abstract class BaseListViewModel<T> : ViewModel() {
    // 网络请求处理
    val dataLoad: SinglePagesLoadRepository<T> by lazy {
        return@lazy SinglePagesLoadRepository<T> { page, rp ->
            dataService(page, rp)
        }
    }

    /**
     *
     */
    protected abstract fun dataService(page: Int, rp: Int): Single<BaseResponse<BaseListData<T>>>
}