package com.memo.business.api

import com.blankj.utilcode.util.LogUtils
import com.google.gson.JsonParseException
import kotlinx.coroutines.TimeoutCancellationException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * title:ExceptionHandler
 * describe:网络请求失败处理
 *
 * @author memo
 * @date 2020-12-18 23:40
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object ApiExceptionHandler {

    fun handleException(exception: Throwable): ApiException {
        //错误日志打印
        LogUtils.eTag("HTTP ERROR", exception.toString())
        return when (exception) {
            // 服务器返回的错误
            is ApiException -> exception
            // 解析错误
            is JsonParseException -> ApiException(ApiCode.ServerError, "数据解析失败")
            // 连接错误
            is SocketTimeoutException,
            is TimeoutCancellationException,
            is ConnectException,
            is SocketException -> ApiException(ApiCode.ServerError, "服务器连接异常，请稍后重试")
            // 网络错误
            is UnknownHostException -> ApiException(ApiCode.NetError, "网络异常，请检查网络")
            // 未知错误
            else -> ApiException(ApiCode.ServerError, "请求失败，请稍后重试")
        }
    }
}