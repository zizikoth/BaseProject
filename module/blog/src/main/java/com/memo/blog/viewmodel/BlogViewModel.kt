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

    // 公众号
    val chapterLiveData by lazy { MutableLiveData<ArrayList<Chapter>>() }
    // 公众号文章
    val articleLiveData by lazy { MutableLiveData<ListEntity<Article>>() }

    /**
     * 获取公众号
     */
    fun getChapters() {
        request(ApiRepository.getChapters(), onSuccess = chapterLiveData::postValue)
    }

    /**
     * 获取公众号文章
     * @param cid Int           公众号id
     * @param pageNum Int       页码
     * @param keyword String    关键字
     */
    fun getChapterArticle(cid: Int, pageNum: Int, keyword: String = "") {
        request(ApiRepository.getChapterArticle(cid, pageNum, keyword), onSuccess = articleLiveData::postValue)
    }

}