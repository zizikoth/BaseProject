package com.memo.project.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.Chapter
import com.memo.business.entity.remote.ListEntity
import com.memo.project.repository.ProjectRepository

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
    private val repository = ProjectRepository()
    val chapterLiveData = MutableLiveData<ArrayList<Chapter>>()
    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    fun getNewProjectArticles(pageNum: Int) {
        request(repository.getNewProject(pageNum), articleLiveData::postValue)
    }

    fun getProjects() {
        request(repository.getProjects(), chapterLiveData::postValue)
    }

    fun getProjectArticle(cid: Int, pageNum: Int) {
        request(repository.getProjectArticles(cid, pageNum), articleLiveData::postValue)
    }
}