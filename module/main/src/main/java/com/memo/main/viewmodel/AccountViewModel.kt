package com.memo.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.base.api.ApiRepository
import com.memo.base.base.BaseViewModel
import com.memo.base.entity.remote.UserInfo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-06-03 13:39
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@FlowPreview
class AccountViewModel : BaseViewModel() {

    val liveData = MutableLiveData<UserInfo>()

    fun login(username: String, password: String) {
        showLoading()
        request(ApiRepository.login(username, password), onSuccess = liveData::postValue)
    }

    fun register(username: String, password: String) {
        showLoading()
        val concat = ApiRepository.register(username, password).flatMapConcat {
            ApiRepository.login(username, password)
        }
        request(concat, onSuccess = liveData::postValue)
    }
}