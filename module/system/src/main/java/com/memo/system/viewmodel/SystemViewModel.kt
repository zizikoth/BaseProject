package com.memo.system.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.Category
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.Navi

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

    val chapterLiveData = MutableLiveData<ArrayList<Category>>()

    val naviLiveData = MutableLiveData<ArrayList<Navi>>()

    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    fun getSystem() {
        request(ApiRepository.getSystem(), chapterLiveData::postValue)
    }

    fun getSystemArticle(cid: Int, pageNum: Int) {
        request(ApiRepository.getSystemArticles(pageNum, cid), articleLiveData::postValue)
    }

    fun getNavi() {
        request(ApiRepository.getNaviWebsite(), naviLiveData::postValue)
    }
}