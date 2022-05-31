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
class MainRepository {

    fun getBanner(): Flow<ArrayList<Article>> {
        return RxHttp.get("/banner/json")
            .toFlowResponse()
    }

    fun getArticles(page: Int): Flow<ArticleListEntity> {
        return RxHttp.get("/article/list/%d/json", page)
            .toFlowResponse()
    }

}