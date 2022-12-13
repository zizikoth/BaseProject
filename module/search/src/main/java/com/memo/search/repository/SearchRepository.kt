package com.memo.search.repository

import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.HotKey
import com.memo.business.entity.remote.ListEntity
import com.memo.business.manager.DataManager
import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-10 14:10
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SearchRepository {

    fun getLocalHistory(): ArrayList<String> = DataManager.getSearchHistory()

    fun addLocalHistory(keyword: String): ArrayList<String> = DataManager.addSearchHistory(keyword)

    fun clearLocalHistory() = DataManager.clearSearchHistory()

    fun getHotKey(): Flow<ArrayList<HotKey>> {
        return RxHttp.get("/hotkey/json").toFlowResponse()
    }

    fun searchArticle(keyword: String, pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.postForm("/article/query/%d/json", pageNum).add("k", keyword).add("page_size", 40).toFlowResponse()
    }
}