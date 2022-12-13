package com.memo.main.repository

import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.Chapter
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
class HomeRepository {

    fun getBanner(): Flow<ArrayList<Article>> {
        return RxHttp.get("/banner/json").toFlowResponse()
    }

    fun getChapters(): Flow<ArrayList<Chapter>> {
        return RxHttp.get("/wxarticle/chapters/json").toFlowResponse()
    }

    fun getTopArticles(): Flow<ArrayList<Article>> {
        return RxHttp.get("/article/top/json").toFlowResponse()
    }

    fun getNewProject(): Flow<ListEntity<Article>> {
        return RxHttp.get("/article/listproject/0/json").toFlowResponse()
    }

    fun getArticles(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/article/list/%d/json", pageNum)
            .add("page_size", 40)
            .toFlowResponse()
    }
}