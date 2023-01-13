package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity

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
    val listLiveData = MutableLiveData<ListEntity<Article>>()
    val addLiveData = MutableLiveData<Article>()
    val editLiveData = MutableLiveData<Article>()
    val deleteLiveData = MutableLiveData<Int>()

    fun getArticleCollectList(pageNum: Int) {
        request(ApiRepository.getArticleCollectList(pageNum), listLiveData::postValue)
    }

    fun addOuterArticleCollect(title: String, author: String, link: String) {
        showLoading()
        request(ApiRepository.addOuterArticleCollect(title, author, link), addLiveData::postValue)
    }

    fun editOuterArticleCollect(id: Int, title: String, author: String, link: String) {
        showLoading()
        request(ApiRepository.editOuterArticleCollect(id, title, author, link)) {
            editLiveData.postValue(Article(id = id, title = title, author = author, link = link))
        }
    }

    fun deleteCollectInCollect(articleId: Int, originId: Int) {
        showLoading()
        request(ApiRepository.deleteCollectInCollect(articleId, originId)) {
            deleteLiveData.postValue(articleId)
        }
    }
}