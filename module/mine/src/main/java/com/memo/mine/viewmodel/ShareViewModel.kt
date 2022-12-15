package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.business.entity.remote.UserShareRecord
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
    val shareLiveData = MutableLiveData<UserShareRecord>()

    fun getSquareShareArticles(pageNum: Int) {
        request(repository.getSquareShareArticles(pageNum), articleLiveData::postValue)
    }

    fun getUserShareArticles(userId: Int, pageNum: Int) {
        request(repository.getUserShareArticles(userId, pageNum), shareLiveData::postValue)
    }
}