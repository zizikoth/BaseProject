package com.memo.base.manager

import com.memo.base.entity.remote.TodoInfo
import com.memo.core.utils.livedata.ProtectedLiveData

/**
 * title: 全局事件消息通知管理
 * describe:
 *
 * @author memo
 * @date 2022-12-07 15:25
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object BusManager {

    /**
     * 用户信息更新
     */
    val userLiveData by lazy { ProtectedLiveData<Boolean>() }

    /**
     * 收藏数据更新
     */
    val collectLiveData by lazy { ProtectedLiveData<Boolean>() }

    /**
     * Todo数据更新
     */
    val todoLiveData by lazy { ProtectedLiveData<TodoInfo>() }

}