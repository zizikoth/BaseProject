package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity

/**
 * title:分享广场
 * describe:
 *
 * @author memo
 * @date 2023-01-16 14:25
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SquareViewModel:BaseViewModel() {

    // 分享列表
    val listLiveData = MutableLiveData<ListEntity<Article>>()

    fun getSquareShareArticles(pageNum: Int) {
        request(ApiRepository.getSquareShareArticles(pageNum), listLiveData::postValue)
    }
}