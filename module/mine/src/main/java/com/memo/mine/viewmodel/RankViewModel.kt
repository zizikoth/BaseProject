package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.RankRecord

/**
 * title:排名
 * describe:
 *
 * @author memo
 * @date 2023-01-16 14:09
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class RankViewModel:BaseViewModel() {
    // 列表
    val listLiveData = MutableLiveData<ListEntity<RankRecord>>()

    // 获取列表
    fun getRankList(pageNum: Int) {
        request(ApiRepository.getRankList(pageNum), listLiveData::postValue)
    }

}