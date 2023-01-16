package com.memo.project.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity

/**
 * title:最新项目
 * describe:
 *
 * @author memo
 * @date 2023-01-16 14:42
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class NewProjectViewModel:BaseViewModel() {
    // 分类文章
    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    /**
     * 获取最新项目
     * @param pageNum Int
     */
    fun getNewProjectArticles(pageNum: Int) {
        request(ApiRepository.getNewProject(pageNum), articleLiveData::postValue)
    }
}