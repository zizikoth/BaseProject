package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.Article
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.UserShareRecord

/**
 * title:分享
 * describe:
 *
 * @author memo
 * @date 2022-12-13 20:57
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ShareViewModel : BaseViewModel() {
    // 用户个人分享
    val shareLiveData = MutableLiveData<UserShareRecord>()

    fun getUserShareArticles(userId: Int, pageNum: Int) {
        request(ApiRepository.getUserShareArticles(userId, pageNum), shareLiveData::postValue)
    }
}