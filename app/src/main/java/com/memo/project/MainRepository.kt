package com.memo.project

import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:网络请求仓
 * describe:
 *
 * @author memo
 * @date 2022-03-17 10:50
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object MainRepository {
    fun getInformList(): Flow<Inform> {
        return RxHttp.postJson("/inform/myInformList")
            .add("pageNum", 1)
            .add("pageSize", 30)
            .toFlowResponse()
    }

    fun getBanner(): Flow<Banner> {
        return RxHttp.postJson("/information/carouselList")
            .add("pageNum", 1)
            .add("pageSize", 5)
            .toFlowResponse()
    }

}