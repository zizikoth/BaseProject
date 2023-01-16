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
    // 体系分类
    val chapterLiveData = MutableLiveData<ArrayList<Category>>()
    // 导航分类
    val naviLiveData = MutableLiveData<ArrayList<Navi>>()
    // 体系文章
    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    /**
     * 获取体系
     */
    fun getSystem() {
        request(ApiRepository.getSystem(), chapterLiveData::postValue)
    }

    /**
     * 获取体系文章
     * @param cid Int       体系id
     * @param pageNum Int   页码
     */
    fun getSystemArticle(cid: Int, pageNum: Int) {
        request(ApiRepository.getSystemArticles(pageNum, cid), articleLiveData::postValue)
    }

    /**
     * 获取导航
     */
    fun getNavi() {
        request(ApiRepository.getNaviWebsite(), naviLiveData::postValue)
    }
}