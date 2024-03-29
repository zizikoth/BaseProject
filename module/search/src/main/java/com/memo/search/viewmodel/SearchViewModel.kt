package com.memo.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.HotKey
import com.memo.base.entity.remote.ListEntity
import com.memo.base.manager.DataManager

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

    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    fun searchArticle(keyword: String, pageNum: Int) {
        request(ApiRepository.searchArticle(keyword, pageNum), articleLiveData::postValue)
    }
}