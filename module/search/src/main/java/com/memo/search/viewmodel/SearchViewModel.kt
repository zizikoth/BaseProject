package com.memo.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.HotKey
import com.memo.business.entity.remote.ListEntity
import com.memo.search.repository.SearchRepository

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-10 14:09
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SearchViewModel : BaseViewModel() {
    private val repository = SearchRepository()

    val hotLiveData = MutableLiveData<ArrayList<HotKey>>()

    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    fun getLocalHistory() = repository.getLocalHistory()

    fun addLocalHistory(keyword: String) = repository.addLocalHistory(keyword)

    fun clearLocalHistory() = repository.clearLocalHistory()

    fun getHotKey() = request(repository.getHotKey(), hotLiveData::postValue)

    fun searchArticle(keyword: String, pageNum: Int) {
        request(repository.searchArticle(keyword, pageNum), articleLiveData::postValue)
    }
}