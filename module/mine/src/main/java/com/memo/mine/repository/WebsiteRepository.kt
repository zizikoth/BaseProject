package com.memo.mine.repository

import com.memo.business.entity.remote.WebUrl
import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-16 13:41
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class WebsiteRepository {

    /**
     * 获取收藏网址列表
     * @return Flow<ArrayList<WebSite>>
     */
    fun getWebSiteCollectList(): Flow<ArrayList<WebUrl>> {
        return RxHttp.get("/lg/collect/usertools/json").toFlowResponse()
    }

    /**
     * 添加收藏网址
     * @param name String   标题
     * @param link String   地址
     * @return Flow<WebSite>
     */
    fun addWebSiteCollect(name: String, link: String): Flow<WebUrl> {
        return RxHttp.postForm("/lg/collect/addtool/json").add("name", name).add("link", link).toFlowResponse()
    }

    /**
     * 修改收藏网址
     * @param id Int        收藏id
     * @param name String   标题
     * @param link String   地址
     * @return Flow<WebSite>
     */
    fun editWebSiteCollect(id: Int, name: String, link: String): Flow<WebUrl> {
        return RxHttp.postForm("/lg/collect/updatetool/json").add("id", id).add("name", name).add("link", link).toFlowResponse()
    }

    /**
     * 取消收藏网址
     * @param id Int    收藏id
     * @return Flow<Any?>
     */
    fun deleteWebSiteCollect(id: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/collect/deletetool/json").add("id", id).toFlowResponse()
    }

}