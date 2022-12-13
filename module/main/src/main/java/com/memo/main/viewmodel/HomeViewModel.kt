package com.memo.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.local.Zip5Null
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.Chapter
import com.memo.business.entity.remote.ListEntity
import com.memo.main.repository.HomeRepository
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

    private val repository = HomeRepository()

    val liveData =
        MutableLiveData<Zip5Null<ArrayList<Article>, ArrayList<Chapter>, ArrayList<Article>, ListEntity<Article>, ListEntity<Article>>>()

    fun getData() {
        val combine = combine(
            repository.getBanner(),
            repository.getChapters(),
            repository.getTopArticles(),
            repository.getNewProject(),
            repository.getArticles(0)) { bannerRes, chapterRes, topArticleRes, projectRes, articleRes ->
            Zip5Null(bannerRes, chapterRes, topArticleRes, projectRes, articleRes)
        }
        request(combine, onSuccess = liveData::postValue)
    }

    fun getArticles(pageNum: Int) {
        showLoading()
        request(repository.getArticles(pageNum)) {
            liveData.postValue(Zip5Null(null, null, null, null, it))
        }
    }
}