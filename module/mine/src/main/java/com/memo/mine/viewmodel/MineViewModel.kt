package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.local.Zip4
import com.memo.base.entity.remote.CoinRecord
import com.memo.base.entity.remote.ListEntity
import com.memo.base.entity.remote.RankRecord
import com.memo.base.manager.DataManager
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

    val dotLiveData = MutableLiveData<Int>()

    val infoLiveData = MutableLiveData<Zip4<Int, Int, Int, Int>>()

    val coinLiveData = MutableLiveData<ListEntity<CoinRecord>>()

    val rankLiveData = MutableLiveData<ListEntity<RankRecord>>()

    fun unReadMessageCount() {
        requestOnly(ApiRepository.unreadMessageCount(), dotLiveData::postValue)
    }

    fun getMineInfo() {
        val combine = combine(ApiRepository.getCollectSize(), ApiRepository.getCoinInfo()) { collectSize, coinInfo ->
            Zip4(coinInfo.level, collectSize, coinInfo.coinCount, coinInfo.rank)
        }
        requestOnly(combine, infoLiveData::postValue)
    }

    fun getCoinList(pageNum: Int) {
        request(ApiRepository.getCoinList(pageNum), coinLiveData::postValue)
    }

    fun getRankList(pageNum: Int) {
        request(ApiRepository.getRankList(pageNum), rankLiveData::postValue)
    }

    fun loginOut() {
        DataManager.loginOut()
        requestOnly(ApiRepository.loginOut()) {}
    }
}