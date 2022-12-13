package com.memo.system.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.*
import com.memo.system.repository.SystemRepository

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-12 09:24
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SystemViewModel : BaseViewModel() {

    private val repository = SystemRepository()

    val chapterLiveData = MutableLiveData<ArrayList<Category>>()

    val naviLiveData = MutableLiveData<ArrayList<Navi>>()

    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    fun getSystem() {
        request(repository.getSystem(), chapterLiveData::postValue)
    }

    fun getSystemArticle(cid: Int, pageNum: Int) {
        request(repository.getSystemArticles(pageNum, cid), articleLiveData::postValue)
    }

    fun getNavi() {
        request(repository.getNavi(), naviLiveData::postValue)
    }
}