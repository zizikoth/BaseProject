package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.NotifyMessage

/**
 * title:通知
 * describe:
 *
 * @author memo
 * @date 2023-01-01 16:43
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class NotifyViewModel : BaseViewModel() {

    val listLiveData = MutableLiveData<ListEntity<NotifyMessage>>()

    fun getUnReadList(pageNum: Int) {
        request(ApiRepository.unreadMessageList(pageNum), listLiveData::postValue)
    }

    fun getReadList(pageNum: Int) {
        request(ApiRepository.readedMessageList(pageNum), listLiveData::postValue)
    }
}