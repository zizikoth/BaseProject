package com.memo.blog.repository

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
 * @date 2022-12-09 15:35
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class BlogRepository {

    /**
     * 获取公众号列表
     * @return Flow<ArrayList<Article>>
     */
    fun getChapters(): Flow<ArrayList<Chapter>> {
        return RxHttp.get("/wxarticle/chapters/json").toFlowResponse()
    }

    /**
     * 获取公众号下的文章
     * @param cid Int           公众号id
     * @param pageNum Int          页码
     * @param keyword String    搜索关键字
     * @return Flow<ListEntity<Article>>
     */
    fun getChapterArticle(cid: Int, pageNum: Int, keyword: String): Flow<ListEntity<Article>> {
        return RxHttp.get("/wxarticle/list/%d/%d/json", cid, pageNum).add("page_size", 40).add("k", keyword).toFlowResponse()
    }

}