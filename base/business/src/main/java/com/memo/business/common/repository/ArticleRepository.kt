package com.memo.business.common.repository

import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:公共Repository
 * describe:
 *
 * @author memo
 * @date 2022-12-09 13:59
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ArticleRepository {

    /**
     * 新增收藏
     * @param id Int    文章id
     * @return Flow<Any?>
     */
    fun addCollect(id: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/collect/%d/json", id).toFlowResponse()
    }

    /**
     * 取消收藏站内文章
     * @param id Int
     * @return Flow<Any?>
     */
    fun deleteInnerCollect(id: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/uncollect_originId/%d/json", id).toFlowResponse()
    }

    /**
     * 取消收藏站外文章
     * @param id Int
     * @param originId Int
     * @return Flow<Any?>
     */
    fun deleteOuterCollect(id:Int,originId:Int):Flow<Any?> {
        return RxHttp.postForm("/lg/uncollect/%d/json",id).add("originId",originId).toFlowResponse()
    }
}