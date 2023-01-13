package com.memo.base.api

import androidx.annotation.Keep

/**
 * title:封装网络请求的基类
 * describe:
 *
 * @author memo
 * @date 2022-03-17 09:07
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@Keep
data class ApiResponse<T>(var data: T, var errorCode: Int = ApiCode.Success, var errorMsg: String = "") {
    fun success() = errorCode == ApiCode.Success
}