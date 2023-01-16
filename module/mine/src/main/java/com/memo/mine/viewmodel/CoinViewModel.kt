package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.CoinRecord
import com.memo.base.entity.remote.ListEntity

/**
 * title:积分
 * describe:
 *
 * @author memo
 * @date 2023-01-16 14:07
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class CoinViewModel : BaseViewModel() {

    // 积分列表
    val listLiveData = MutableLiveData<ListEntity<CoinRecord>>()

    // 获取积分列表
    fun getCoinList(pageNum: Int) {
        request(ApiRepository.getCoinList(pageNum), listLiveData::postValue)
    }

}