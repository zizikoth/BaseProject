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

    fun getCollectArticles(pageNum: Int) {
        request(repository.getCollectArticles(pageNum), articleLiveData::postValue)
    }

    fun unCollectArticleInCollect(articleId: Int, originId: Int) {
        showLoading()
        request(repository.unCollectArticleInCollect(articleId, originId)) {
            collectLiveData.postValue(articleId)
        }
    }
}