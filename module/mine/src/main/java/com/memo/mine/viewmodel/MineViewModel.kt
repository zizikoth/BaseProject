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
 * title:我的
 * describe:
 *
 * @author memo
 * @date 2022-12-12 16:30
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class MineViewModel : BaseViewModel() {

    // 未读消息
    val dotLiveData = MutableLiveData<Int>()
    // 用户展示数据
    val infoLiveData = MutableLiveData<Zip4<Int, Int, Int, Int>>()

    // 未读消息
    fun unReadMessageCount() {
        requestOnly(ApiRepository.unreadMessageCount(), dotLiveData::postValue)
    }

    // 个人信息展示
    fun getMineInfo() {
        val combine = combine(ApiRepository.getArticleCollectList(1), ApiRepository.getCoinInfo()) { collectInfo, coinInfo ->
            Zip4(coinInfo.level, collectInfo.total, coinInfo.coinCount, coinInfo.rank)
        }
        requestOnly(combine, infoLiveData::postValue)
    }

    fun loginOut() {
        DataManager.loginOut()
        requestOnly(ApiRepository.loginOut()) {}
    }
}