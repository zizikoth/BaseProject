package com.memo.project

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.local.Zip2Null
import kotlinx.coroutines.flow.combine

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-03-17 14:25
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class MainViewModel : BaseViewModel() {

    val dataLiveData = MutableLiveData<Zip2Null<Banner, Inform>>()

    fun getInformList() {
        request(
            request = MainRepository.getInformList(),
            onSuccess = { dataLiveData.postValue(Zip2Null(null, it)) }
        )
    }

    fun getAll() {
        val combine = combine(MainRepository.getBanner(), MainRepository.getInformList()) { banner, inform ->
            Zip2Null(banner, inform)
        }
        request(
            request = combine,
            onSuccess = { dataLiveData.postValue(it) }
        )
    }
}