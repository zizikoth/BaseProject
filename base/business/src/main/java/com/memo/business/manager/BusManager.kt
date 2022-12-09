package com.memo.business.manager

import com.memo.business.entity.remote.UserInfo
import com.memo.core.utils.livedata.ProtectedLiveData

/**
 * title: 全局消息通知管理
 * describe:
 *
 * @author memo
 * @date 2022-12-07 15:25
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object BusManager {

    val userLiveData by lazy { ProtectedLiveData<UserInfo>() }

}