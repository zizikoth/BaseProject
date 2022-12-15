package com.memo.mine.repository

import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.business.entity.remote.UserShareRecord
import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-13 20:55
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ShareRepository {

    fun getSquareShareArticles(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/user_article/list/%d/json", pageNum).add("page_size", 40).toFlowResponse()
    }

    fun getUserShareArticles(userId: Int, pageNum: Int): Flow<UserShareRecord> {
        return RxHttp.get("/user/%d/share_articles/%d/json", userId, pageNum).add("page_size", 40).toFlowResponse()
    }
}