package com.memo.project.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.Chapter
import com.memo.base.entity.remote.ListEntity

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-10 12:06
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ProjectViewModel : BaseViewModel() {
    val chapterLiveData = MutableLiveData<ArrayList<Chapter>>()
    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    fun getNewProjectArticles(pageNum: Int) {
        request(ApiRepository.getNewProject(pageNum), articleLiveData::postValue)
    }

    fun getProjects() {
        request(ApiRepository.getProjects(), chapterLiveData::postValue)
    }

    fun getProjectArticle(cid: Int, pageNum: Int) {
        request(ApiRepository.getProjectArticles(cid, pageNum), articleLiveData::postValue)
    }
}