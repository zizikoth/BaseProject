package com.memo.mine.repository

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
 * @date 2022-12-13 21:53
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class CollectRepository {

    fun getInnerCollectArticles(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/lg/collect/list/%d/json", pageNum).add("page_size", 40).toFlowResponse()
    }

    fun addInnerCollectArticle(articleId: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/collect/%d/json", articleId).toFlowResponse()
    }

    fun removeInnerCollectArticle(articleId: Int, originId: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/uncollect/%d/json", articleId)
            .add("originId", originId).toFlowResponse()
    }
}