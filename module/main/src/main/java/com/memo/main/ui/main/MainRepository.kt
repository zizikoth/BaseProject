package com.memo.main.ui.main

import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-06-01 14:40
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class MainRepository {
    fun getBanner(): Flow<ArrayList<Article>> {
        return RxHttp.get("/banner/json")
            .toFlowResponse()
    }

    fun getArticles(page: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/article/list/%d/json", page)
            .toFlowResponse()
    }
}