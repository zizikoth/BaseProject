package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity

/**
 * title:收藏
 * describe:
 *
 * @author memo
 * @date 2022-12-13 22:03
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class CollectViewModel : BaseViewModel() {
    // 查
    val listLiveData = MutableLiveData<ListEntity<Article>>()
    // 增
    val addLiveData = MutableLiveData<Article>()
    // 改
    val editLiveData = MutableLiveData<Article>()
    // 删
    val deleteLiveData = MutableLiveData<Int>()

    /**
     * 获取用户收藏列表
     * @param pageNum Int 页码
     */
    fun getArticleCollectList(pageNum: Int) {
        request(ApiRepository.getArticleCollectList(pageNum), listLiveData::postValue)
    }

    /**
     * 新增站外文章收藏
     * @param title String  标题
     * @param author String 作者
     * @param link String   链接
     */
    fun addOuterArticleCollect(title: String, author: String, link: String) {
        showLoading()
        request(ApiRepository.addOuterArticleCollect(title, author, link), addLiveData::postValue)
    }

    /**
     * 修改站外文章收藏
     * @param id Int        收藏id
     * @param title String  标题
     * @param author String 作者
     * @param link String   链接
     */
    fun editOuterArticleCollect(id: Int, title: String, author: String, link: String) {
        showLoading()
        request(ApiRepository.editOuterArticleCollect(id, title, author, link)) {
            editLiveData.postValue(Article(id = id, title = title, author = author, link = link))
        }
    }

    /**
     * 删除收藏 在收藏列表中
     * @param collectId Int 收藏id
     * @param originId Int  文章初始id
     */
    fun deleteCollectInCollect(collectId: Int, originId: Int) {
        showLoading()
        request(ApiRepository.deleteCollectInCollect(collectId, originId)) {
            deleteLiveData.postValue(collectId)
        }
    }
}