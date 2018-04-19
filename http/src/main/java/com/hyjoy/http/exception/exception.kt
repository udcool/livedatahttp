package com.hyjoy.http.exception


/**
 * Created by hyjoy on 2018/4/19.
 */
class PageDataException : ServerException("数据异常", "listDataResponse的listData不能为空")


class CallDataNullException : ServerException("数据异常", "response不能为空")
