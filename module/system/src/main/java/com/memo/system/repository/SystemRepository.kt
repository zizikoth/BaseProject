package com.memo.system.repository

import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.Category
import com.memo.business.entity.remote.ListEntity
import com.memo.business.entity.remote.Navi
import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-12 09:24
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SystemRepository {
    fun getSystem(): Flow<ArrayList<Category>> {
        return RxHttp.get("/tree/json").toFlowResponse()
    }

    fun getSystemArticles(pageNum: Int, cid: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/article/list/%d/json", pageNum).add("cid", cid)
            .add("page_size", 40).toFlowResponse()
    }

    fun getNavi(): Flow<ArrayList<Navi>> {
        return RxHttp.get("/navi/json").toFlowResponse()
    }
}