package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.mine.repository.ShareRepository

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-13 20:57
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ShareViewModel : BaseViewModel() {
    private val repository = ShareRepository()
    val articleLiveData = MutableLiveData<ListEntity<Article>>()

    fun getSquareArticles(pageNum: Int) {
        request(repository.getSquareArticles(pageNum), articleLiveData::postValue)
    }
}