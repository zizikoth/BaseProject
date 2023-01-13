package com.memo.base.api

/**
 * title: Api请求错误码
 * describe:
 *
 * @author memo
 * @date 2022-03-17 09:04
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object ApiCode {
    // 成功
    const val Success = 0
    // 网络错误
    const val NetError = 404
    // 服务器异常
    const val ServerError = 500
    // 用户信息失效
    const val TokenError = -1001
}