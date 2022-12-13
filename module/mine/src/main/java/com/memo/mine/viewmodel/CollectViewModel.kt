package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.mine.repository.CollectRepository

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-13 22:03
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class CollectViewModel : BaseViewModel() {
    private val repository = CollectRepository()
    val articleLiveData = MutableLiveData<ListEntity<Article>>()
    val collectLiveData = MutableLiveData<Int>()

    fun getArticles(pageNum: Int) {
        request(repository.getInnerCollectArticles(pageNum), articleLiveData::postValue)
    }

    fun unCollectArticle(articleId: Int, originId: Int) {
        showLoading()
        request(repository.removeInnerCollectArticle(articleId, originId)) {
            collectLiveData.postValue(articleId)
        }
    }
}