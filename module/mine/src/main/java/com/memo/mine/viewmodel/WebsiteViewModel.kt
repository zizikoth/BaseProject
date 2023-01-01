package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.WebUrl

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

    val listLiveData = MutableLiveData<ArrayList<WebUrl>>()
    val addLiveData = MutableLiveData<WebUrl>()
    val editLiveData = MutableLiveData<WebUrl>()
    val deleteLiveData = MutableLiveData<Int>()

    fun getWebsiteCollectList() {
        return request(ApiRepository.getWebSiteCollectList(), listLiveData::postValue)
    }

    fun addWebsiteCollect(name: String, link: String) {
        showLoading()
        return request(ApiRepository.addWebSiteCollect(name, link), addLiveData::postValue)
    }

    fun editWebsiteCollect(id: Int, name: String, link: String) {
        showLoading()
        return request(ApiRepository.editWebSiteCollect(id, name, link), editLiveData::postValue)
    }

    fun deleteWebsiteCollect(id: Int) {
        showLoading()
        return request(ApiRepository.deleteWebSiteCollect(id)) {
            deleteLiveData.postValue(id)
        }
    }
}