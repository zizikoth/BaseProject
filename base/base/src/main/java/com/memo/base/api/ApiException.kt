package com.memo.base.api

import java.io.IOException


/**
 * title:封装请求异常的返回错误
 * describe:
 *
 * @author memo
 * @date 2022-03-17 09:07
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ApiException(val code: Int, override val message: String) : IOException(message) {
    override fun toString() = "ApiException(code=$code, message='$message')"
}