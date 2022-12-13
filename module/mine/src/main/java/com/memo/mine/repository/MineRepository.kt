package com.memo.mine.repository

import com.memo.business.entity.remote.CoinInfo
import com.memo.business.manager.DataManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-12 16:30
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class MineRepository {

    fun getCollectSize(): Flow<Int> {
        return flowOf(DataManager.getUser()?.collectIds?.size?:0)
    }

    fun getCoinInfo(): Flow<CoinInfo> {
        return RxHttp.get("/lg/coin/userinfo/json").toFlowResponse<CoinInfo>()
    }
}