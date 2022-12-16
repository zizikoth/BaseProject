package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.WebUrl
import com.memo.mine.repository.WebsiteRepository

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-16 13:42
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class WebsiteViewModel : BaseViewModel() {
    private val repository = WebsiteRepository()

    val listLiveData = MutableLiveData<ArrayList<WebUrl>>()
    val addLiveData = MutableLiveData<WebUrl>()
    val editLiveData = MutableLiveData<WebUrl>()
    val deleteLiveData = MutableLiveData<Int>()

    fun getWebsiteCollectList() {
        return request(repository.getWebSiteCollectList(), listLiveData::postValue)
    }

    fun addWebsiteCollect(name: String, link: String) {
        showLoading()
        return request(repository.addWebSiteCollect(name, link), addLiveData::postValue)
    }

    fun editWebsiteCollect(id: Int, name: String, link: String) {
        showLoading()
        return request(repository.editWebSiteCollect(id, name, link), editLiveData::postValue)
    }

    fun deleteWebsiteCollect(id: Int) {
        showLoading()
        return request(repository.deleteWebSiteCollect(id)) {
            deleteLiveData.postValue(id)
        }
    }
}