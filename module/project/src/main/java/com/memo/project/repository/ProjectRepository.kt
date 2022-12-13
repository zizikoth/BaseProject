package com.memo.project.repository

import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.Chapter
import com.memo.business.entity.remote.ListEntity
import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:数据仓
 * describe:
 *
 * @author memo
 * @date 2022-12-10 11:58
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ProjectRepository {

    /**
     * 获取最新项目
     * @param pageNum Int 页码
     * @return Flow<ListEntity<Article>>
     */
    fun getNewProject(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/article/listproject/%d/json", pageNum).add("page_size", 40).toFlowResponse()
    }

    /**
     * 获取项目类型
     * @return Flow<ArrayList<Chapter>>
     */
    fun getProjects(): Flow<ArrayList<Chapter>> {
        return RxHttp.get("/project/tree/json").toFlowResponse()
    }

    /**
     * 获取项目类型文章
     * @param cid Int   项目类型id
     * @param pageNum Int  页码
     * @return Flow<ListEntity<Article>>
     */
    fun getProjectArticles(cid: Int, pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/project/list/%d/json", pageNum).add("page_size", 40).add("cid", cid).toFlowResponse()
    }
}