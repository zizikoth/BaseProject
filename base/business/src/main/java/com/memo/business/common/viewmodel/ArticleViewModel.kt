package com.memo.business.common.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.common.repository.ArticleRepository

/**
 * title: 公共ViewModel
 * describe:
 *
 * @author memo
 * @date 2022-12-09 13:59
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ArticleViewModel : BaseViewModel() {
    private val repository = ArticleRepository()

    val collectLiveData = MutableLiveData<Boolean>()

    /**
     * 添加收藏
     * @param articleId Int
     */
    fun addCollect(articleId: Int) {
        return request(repository.addCollect(articleId)) {
            collectLiveData.postValue(true)
        }
    }

    /**
     * 取消收藏站内文章
     * @param articleId Int
     */
    fun deleteInnerCollect(articleId: Int) {
        return request(repository.deleteInnerCollect(articleId)) {
            collectLiveData.postValue(false)
        }
    }

    /**
     * 取消收藏站外文章
     * @param articleId Int
     * @param collectId Int
     */
    fun deleteOuterCollect(articleId: Int, collectId: Int) {
        return request(repository.deleteOuterCollect(collectId, articleId)) {
            collectLiveData.postValue(false)
        }
    }
}