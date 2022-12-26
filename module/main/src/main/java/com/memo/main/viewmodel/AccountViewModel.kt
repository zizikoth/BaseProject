package com.memo.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.memo.business.base.BaseViewModel
import com.memo.business.entity.remote.UserInfo
import com.memo.main.repository.AccountRepository
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

    private val repository = AccountRepository()

    val liveData = MutableLiveData<UserInfo>()

    fun login(username: String, password: String) {
        showLoading()
        request(repository.login(username, password), onSuccess = liveData::postValue)
    }

    fun register(username: String, password: String) {
        showLoading()
        val concat = repository.register(username, password).flatMapConcat {
            repository.login(username, password)
        }
        request(concat, onSuccess = liveData::postValue)
    }
}