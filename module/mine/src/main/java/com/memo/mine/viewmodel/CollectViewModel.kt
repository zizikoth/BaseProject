package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.business.entity.remote.WebSite
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
    val articleListLiveData = MutableLiveData<ListEntity<Article>>()
    val deleteLiveData = MutableLiveData<Int>()

    val websiteListLiveData = MutableLiveData<ArrayList<WebSite>>()
    val editLiveData = MutableLiveData<WebSite>()

    fun getCollectArticles(pageNum: Int) {
        request(repository.getCollectArticles(pageNum), articleListLiveData::postValue)
    }

    fun unCollectArticleInCollect(articleId: Int, originId: Int) {
        showLoading()
        request(repository.unCollectArticleInCollect(articleId, originId)) {
            deleteLiveData.postValue(articleId)
        }
    }

    fun getWebSiteCollectList() {
        request(repository.getWebSiteCollectList(), websiteListLiveData::postValue)
    }

    fun addWebSiteCollect(name: String, link: String) {
        showLoading()
        request(repository.collectWebSite(name, link), editLiveData::postValue)
    }

    fun editWebSiteCollect(id: Int, name: String, link: String) {
        showLoading()
        request(repository.editWebSiteCollect(id, name, link), editLiveData::postValue)
    }

    fun deleteWebSiteCollect(id: Int) {
        showLoading()
        request(repository.unCollectWebSite(id)) {
            deleteLiveData.postValue(id)
        }
    }

}