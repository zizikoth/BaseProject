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
    // 分类
    val chapterLiveData = MutableLiveData<ArrayList<Chapter>>()
    // 分类文章
    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    // 项目分类
    fun getProjects() {
        request(ApiRepository.getProjects(), chapterLiveData::postValue)
    }

    /**
     * 项目分类文章
     * @param cid Int       分类id
     * @param pageNum Int   页码
     */
    fun getProjectArticle(cid: Int, pageNum: Int) {
        request(ApiRepository.getProjectArticles(cid, pageNum), articleLiveData::postValue)
    }
}