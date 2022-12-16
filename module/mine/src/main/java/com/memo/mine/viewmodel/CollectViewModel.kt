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
    val listLiveData = MutableLiveData<ListEntity<Article>>()
    val addLiveData = MutableLiveData<Article>()
    val editLiveData = MutableLiveData<Article>()
    val deleteLiveData = MutableLiveData<Int>()

    fun getArticleCollectList(pageNum: Int) {
        request(repository.getArticleCollectList(pageNum), listLiveData::postValue)
    }

    fun addOuterArticleCollect(title: String, author: String, link: String) {
        showLoading()
        request(repository.addOuterArticleCollect(title, author, link), addLiveData::postValue)
    }

    fun editOuterArticleCollect(id: Int, title: String, author: String, link: String) {
        showLoading()
        request(repository.editOuterArticleCollect(id, title, author, link)) {
            editLiveData.postValue(Article(id = id, title = title, author = author, link = link))
        }
    }

    fun deleteArticleCollect(articleId: Int, originId: Int) {
        showLoading()
        request(repository.deleteArticleCollect(articleId, originId)) {
            deleteLiveData.postValue(articleId)
        }
    }
}