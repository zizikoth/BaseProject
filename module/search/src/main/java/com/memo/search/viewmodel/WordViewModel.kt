package com.memo.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.HotKey
import com.memo.base.manager.DataManager

/**
 * title:搜索词
 * describe:
 *
 * @author memo
 * @date 2023-01-16 14:47
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class WordViewModel:BaseViewModel() {
    val wordLiveData = MutableLiveData<ArrayList<HotKey>>()

    fun getLocalHistory() = DataManager.getSearchHistory()

    fun addLocalHistory(keyword: String) = DataManager.addSearchHistory(keyword)

    fun clearLocalHistory() = DataManager.clearSearchHistory()

    fun getHotKey() = request(ApiRepository.getHotKey(), wordLiveData::postValue)
}