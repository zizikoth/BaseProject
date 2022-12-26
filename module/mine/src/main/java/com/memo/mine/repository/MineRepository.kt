package com.memo.mine.repository

import com.memo.business.entity.remote.CoinInfo
import com.memo.business.entity.remote.CoinRecord
import com.memo.business.entity.remote.ListEntity
import com.memo.business.entity.remote.RankRecord
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

    /**
     * 获取用户收藏数量
     * @return Flow<Int>
     */
    fun getCollectSize(): Flow<Int> {
        return flowOf(DataManager.getUser()?.collectIds?.size ?: 0)
    }

    /**
     * 获取用户积分信息
     * @return Flow<CoinInfo>
     */
    fun getCoinInfo(): Flow<CoinInfo> {
        return RxHttp.get("/lg/coin/userinfo/json").toFlowResponse<CoinInfo>()
    }

    /**
     * 获取用户积分记录
     * @param pageNum Int   页码
     * @return Flow<ListEntity<CoinRecord>>
     */
    fun getCoinList(pageNum: Int): Flow<ListEntity<CoinRecord>> {
        return RxHttp.get("/lg/coin/list/%d/json", pageNum).toFlowResponse()
    }

    /**
     * 获取应用排名数据
     * @param pageNum Int   页码
     * @return Flow<ListEntity<RankInfo>>
     */
    fun getRankList(pageNum: Int): Flow<ListEntity<RankRecord>> {
        return RxHttp.get("/coin/rank/%d/json", pageNum).toFlowResponse()
    }

    /**
     * 退出登录
     * @return Flow<Any?>
     */
    fun loginOut(): Flow<Any?> {
        return RxHttp.get("/user/loginout/json").toFlowResponse()
    }
}