package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.local.Zip4
import com.memo.business.entity.remote.CoinRecord
import com.memo.business.entity.remote.ListEntity
import com.memo.business.entity.remote.RankRecord
import com.memo.mine.repository.MineRepository
import kotlinx.coroutines.flow.combine

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-12 16:30
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class MineViewModel : BaseViewModel() {

    private val repository = MineRepository()

    val infoLiveData = MutableLiveData<Zip4<Int, Int, Int, Int>>()

    val coinLiveData = MutableLiveData<ListEntity<CoinRecord>>()

    val rankLiveData = MutableLiveData<ListEntity<RankRecord>>()

    fun getMineInfo() {
        val combine = combine(repository.getCollectSize(), repository.getCoinInfo()) { collectSize, coinInfo ->
            Zip4(coinInfo.level, collectSize, coinInfo.coinCount, coinInfo.rank)
        }
        requestWithoutError(combine, infoLiveData::postValue)
    }

    fun getCoinList(pageNum: Int) {
        request(repository.getCoinList(pageNum), coinLiveData::postValue)
    }

    fun getRankList(pageNum: Int) {
        request(repository.getRankList(pageNum), rankLiveData::postValue)
    }
}