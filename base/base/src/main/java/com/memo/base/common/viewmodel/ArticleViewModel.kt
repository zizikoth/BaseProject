package com.memo.base.common.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel

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

    val collectLiveData = MutableLiveData<Boolean>()

    /**
     * 添加收藏站内文章
     * @param articleId Int 文章的id
     */
    fun addInnerArticleCollect(articleId: Int) {
        return request(ApiRepository.addInnerArticleCollect(articleId)) {
            collectLiveData.postValue(true)
        }
    }

    /**
     * 取消收藏 从列表进入的文章
     * @param articleId Int 文章的id
     */
    fun deleteCollectInDetail(articleId: Int) {
        return request(ApiRepository.deleteCollectInDetail(articleId)) {
            collectLiveData.postValue(false)
        }
    }

    /**
     * 取消收藏 从收藏列表进入的文章
     * @param articleId Int 文章的id
     * @param collectId Int 收藏的id
     */
    fun deleteCollectInCollect(articleId: Int, collectId: Int) {
        return request(ApiRepository.deleteCollectInCollect(collectId, articleId)) {
            collectLiveData.postValue(false)
        }
    }
}