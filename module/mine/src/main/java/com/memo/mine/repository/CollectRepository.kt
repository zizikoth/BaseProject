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

    /**
     * 获取所有收藏文章列表
     * @param pageNum Int 页码
     * @return Flow<ListEntity<Article>>
     */
    fun getArticleCollectList(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/lg/collect/list/%d/json", pageNum).add("page_size", 40).toFlowResponse()
    }

    /**
     * 取消收藏 在收藏列表
     * @param id Int        收藏id
     * @param originId Int  原始文章id 收藏的外部文章为-1
     * @return Flow<Any?>
     */
    fun deleteArticleCollect(id: Int, originId: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/uncollect/%d/json", id).add("originId", originId).toFlowResponse()
    }

    /**
     * 收藏站外文章
     * @param title String  标题
     * @param author String 作者
     * @param link String   地址
     * @return Flow<Article>
     */
    fun addOuterArticleCollect(title: String, author: String, link: String): Flow<Article> {
        return RxHttp.postForm("lg/collect/add/json").add("title", title).add("author", author).add("link", link).toFlowResponse()
    }

    /**
     * 修改收藏的站外文章
     * @param id Int        收藏id
     * @param title String  标题
     * @param author String 作者
     * @param link String   地址
     * @return Flow<Article>
     */
    fun editOuterArticleCollect(id: Int, title: String, author: String, link: String): Flow<Any?> {
        return RxHttp.postForm("/lg/collect/user_article/update/%d/json", id).add("title", title).add("author", author).add("link", link)
            .toFlowResponse()
    }


}