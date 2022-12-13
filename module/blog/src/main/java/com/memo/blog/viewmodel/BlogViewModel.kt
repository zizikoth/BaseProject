package com.memo.blog.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.blog.repository.BlogRepository
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.Chapter
import com.memo.business.entity.remote.ListEntity

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

    private val repository = BlogRepository()

    val chapterLiveData by lazy { MutableLiveData<ArrayList<Chapter>>() }
    val articleLiveData by lazy { MutableLiveData<ListEntity<Article>>() }

    fun getChapters() {
        request(repository.getChapters(), onSuccess = chapterLiveData::postValue)
    }

    fun getChapterArticle(cid: Int, pageNum: Int, keyword: String = "") {
        request(repository.getChapterArticle(cid, pageNum, keyword), onSuccess = articleLiveData::postValue)
    }

}