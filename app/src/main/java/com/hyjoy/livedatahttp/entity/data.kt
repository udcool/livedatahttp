package com.hyjoy.livedatahttp.entity

import com.hyjoy.http.data.ListData
import com.hyjoy.http.data.Response

/**
 * Created by hyjoy on 2018/4/19.
 */
class BaseResponse<T>(
        /**
         * 200 其余状态码都是失败
         */
        val code: Int,
        /**
         * 失败原因
         */
        val message: String?,
        /**
         * 数据
         */
        val data: T?) : Response<T> {
    override fun success(): Boolean {
        return code == 200
    }

    override fun code(): Int {
        return code
    }

    override fun message(): String? {
        return message
    }

    override fun data(): T? {
        return data
    }

    /**
     * 是否成功
     */
    fun successful() = code == 200
}

class BaseListData<T>(
        /**
         * 当前页
         */
        val page: Int,
        /**
         * 显示数据的条数
         */
        val rp: Int,
        /**
         * 数据
         */
        val list: List<T>
) : ListData<T> {
    override fun data(): List<T> {
        return list
    }

    override fun pageNo(): Int {
        return page
    }

    override fun count(): Int {
        return rp
    }
}