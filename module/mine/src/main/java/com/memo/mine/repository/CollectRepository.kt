package com.memo.mine.repository

import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.business.entity.remote.WebSite
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
    fun getCollectArticles(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/lg/collect/list/%d/json", pageNum).add("page_size", 40).toFlowResponse()
    }

    /**
     * 收藏站内文章
     * @param id Int 文章id
     * @return Flow<Any?>
     */
    fun collectInnerArticle(id: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/collect/%d/json", id).toFlowResponse()
    }

    /**
     * 取消收藏 在收藏列表
     * @param id Int        收藏id
     * @param originId Int  原始文章id 收藏的外部文章为-1
     * @return Flow<Any?>
     */
    fun unCollectArticleInCollect(id: Int, originId: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/uncollect/%d/json", id).add("originId", originId).toFlowResponse()
    }

    /**
     * 取消收藏 在详情页面
     * @param id Int    文章id
     * @return Flow<Any?>
     */
    fun unCollectArticleInDetail(id: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/uncollect_originId/%d/json", id).toFlowResponse()
    }

    /**
     * 收藏站外文章
     * @param title String  标题
     * @param author String 作者
     * @param link String   地址
     * @return Flow<Article>
     */
    fun collectOuterArticle(title: String, author: String, link: String): Flow<Article> {
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
    fun editOuterCollectArticle(id: Int, title: String, author: String, link: String): Flow<Article> {
        return RxHttp.postForm("/lg/collect/user_article/update/%d/json", id).add("title", title).add("author", author).add("link", link)
            .toFlowResponse()
    }

    /**
     * 获取收藏网址列表
     * @return Flow<ArrayList<WebSite>>
     */
    fun getWebSiteCollectList(): Flow<ArrayList<WebSite>> {
        return RxHttp.get("/lg/collect/usertools/json").toFlowResponse()
    }

    /**
     * 添加收藏网址
     * @param name String   标题
     * @param link String   地址
     * @return Flow<WebSite>
     */
    fun collectWebSite(name: String, link: String): Flow<WebSite> {
        return RxHttp.postForm("/lg/collect/addtool/json").add("name", name).add("link", link).toFlowResponse()
    }

    /**
     * 修改收藏网址
     * @param id Int        收藏id
     * @param name String   标题
     * @param link String   地址
     * @return Flow<WebSite>
     */
    fun editWebSiteCollect(id: Int, name: String, link: String): Flow<WebSite> {
        return RxHttp.postForm("/lg/collect/updatetool/json").add("id", id).add("name", name).add("link", link).toFlowResponse()
    }

    /**
     * 取消收藏网址
     * @param id Int    收藏id
     * @return Flow<Any?>
     */
    fun unCollectWebSite(id: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/collect/deletetool/json").add("id", id).toFlowResponse()
    }

}