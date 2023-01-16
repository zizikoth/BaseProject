package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.WebUrl

/**
 * title:网址收藏
 * describe:
 *
 * @author memo
 * @date 2022-12-16 13:42
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class WebsiteViewModel : BaseViewModel() {

    // 查
    val listLiveData = MutableLiveData<ArrayList<WebUrl>>()
    // 增
    val addLiveData = MutableLiveData<WebUrl>()
    // 改
    val editLiveData = MutableLiveData<WebUrl>()
    // 删
    val deleteLiveData = MutableLiveData<Int>()

    /**
     * 获取所有收藏列表
     */
    fun getWebsiteCollectList() {
        return request(ApiRepository.getWebSiteCollectList(), listLiveData::postValue)
    }

    /**
     * 新增
     * @param name String   标题
     * @param link String   链接
     */
    fun addWebsiteCollect(name: String, link: String) {
        showLoading()
        return request(ApiRepository.addWebSiteCollect(name, link), addLiveData::postValue)
    }

    /**
     * 修改
     * @param id Int        收藏id
     * @param name String   标题
     * @param link String   链接
     */
    fun editWebsiteCollect(id: Int, name: String, link: String) {
        showLoading()
        return request(ApiRepository.editWebSiteCollect(id, name, link), editLiveData::postValue)
    }

    /**
     * 删除
     * @param id Int    收藏id
     */
    fun deleteWebsiteCollect(id: Int) {
        showLoading()
        return request(ApiRepository.deleteWebSiteCollect(id)) {
            deleteLiveData.postValue(id)
        }
    }
}