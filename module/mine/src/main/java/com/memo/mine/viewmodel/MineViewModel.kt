package com.memo.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.local.Zip4
import com.memo.business.utils.toast
import com.memo.mine.repository.CollectRepository
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
    private val r = CollectRepository()

    val infoLiveData = MutableLiveData<Zip4<Int, Int, Int, Int>>()

    fun getCoinInfo() {
        request(repository.getCoinInfo())
        val combine = combine(repository.getCollectSize(), repository.getCoinInfo()) { collectSize, coinInfo ->
            Zip4(coinInfo.level, collectSize, coinInfo.coinCount, coinInfo.rank)
        }
        request(combine, infoLiveData::postValue)
    }
}