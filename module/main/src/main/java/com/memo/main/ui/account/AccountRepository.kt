package com.memo.main.ui.account

import UserInfo
import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-06-03 13:39
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class AccountRepository {

    fun login(username: String, password: String): Flow<UserInfo> {
        return RxHttp.postForm("user/login")
            .add("username", username)
            .add("password", password)
            .toFlowResponse()
    }

    fun register(username: String, password: String): Flow<Any> {
        return RxHttp.postForm("user/register")
            .add("username", username)
            .add("password", password)
            .toFlowResponse()
    }
}