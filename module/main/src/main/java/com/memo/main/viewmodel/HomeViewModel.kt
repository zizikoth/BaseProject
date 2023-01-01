package com.memo.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.local.Zip5Null
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.Chapter
import com.memo.base.entity.remote.ListEntity
import kotlinx.coroutines.flow.combine

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-08 10:50
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class HomeViewModel : BaseViewModel() {

    val liveData =
        MutableLiveData<Zip5Null<ArrayList<Article>, ArrayList<Chapter>, ArrayList<Article>, ListEntity<Article>, ListEntity<Article>>>()

    fun getData() {
        val combine = combine(
            ApiRepository.getBanner(),
            ApiRepository.getChapters(),
            ApiRepository.getTopArticles(),
            ApiRepository.getNewProject(),
            ApiRepository.getArticles(0)) { bannerRes, chapterRes, topArticleRes, projectRes, articleRes ->
            Zip5Null(bannerRes, chapterRes, topArticleRes, projectRes, articleRes)
        }
        request(combine, onSuccess = liveData::postValue)
    }

    fun getArticles(pageNum: Int) {
        showLoading()
        request(ApiRepository.getArticles(pageNum)) {
            liveData.postValue(Zip5Null(null, null, null, null, it))
        }
    }
}