package com.memo.business.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memo.business.api.ApiCode
import com.memo.business.api.ApiExceptionHandler
import com.memo.business.utils.toast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-03-17 10:21
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
open class BaseViewModel : ViewModel() {
    /*** 是否是第一次加载 ***/
    private var isFirstLoad = true

    /*** 请求加载框 ***/
    val loadingEvent: MutableLiveData<Boolean> = MutableLiveData()

    protected fun <T> request(
        request: Flow<T>,
        onSuccess: ((data: T) -> Unit),
        onError: ((code: Int) -> Unit)? = null,
        showLoading: Boolean = true
    ) {
        viewModelScope.launch {
            // 开始请求
            if (showLoading) loadingEvent.postValue(true)
            request.catch {
                // 请求失败
                val error = ApiExceptionHandler.handleException(it)
                toast(error.message)
                if (error.code == ApiCode.ReLogin501 || error.code == ApiCode.ReLogin502) {
                    // 重新登录
                } else {
                    onError?.invoke(error.code)
                }
            }.collect {
                // 请求成功
                onSuccess(it)
                isFirstLoad = false
            }
            // 结束请求
            if (showLoading) loadingEvent.postValue(false)
        }
    }

}