package com.memo.blog.viewmodel

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
 * @date 2022-12-09 15:34
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class BlogViewModel : BaseViewModel() {

    val chapterLiveData by lazy { MutableLiveData<ArrayList<Chapter>>() }
    val articleLiveData by lazy { MutableLiveData<ListEntity<Article>>() }

    fun getChapters() {
        request(ApiRepository.getChapters(), onSuccess = chapterLiveData::postValue)
    }

    fun getChapterArticle(cid: Int, pageNum: Int, keyword: String = "") {
        request(ApiRepository.getChapterArticle(cid, pageNum, keyword), onSuccess = articleLiveData::postValue)
    }

}