package com.memo.business.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memo.business.api.ApiCode
import com.memo.business.api.ApiExceptionHandler
import com.memo.business.utils.toast
import kotlinx.coroutines.CoroutineScope
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
    var isFirstLoad = true

    /*** 页面状态 ***/
    val stateEvent: MutableLiveData<Int> = MutableLiveData()

    /*** 加载状态 ***/
    val loadEvent: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 显示加载框
     */
    protected fun showLoading() {
        loadEvent.postValue(true)
    }

    /**
     * 隐藏加载框
     */
    protected fun hideLoading() {
        loadEvent.postValue(false)
    }

    /**
     * 开启请求
     * @param request SuspendFunction1<[@kotlin.ParameterName] CoroutineScope, Flow<T>> 请求内容
     * @param onSuccess Function1<[@kotlin.ParameterName] T, Unit>                      成功回调
     * @param onError Function1<[@kotlin.ParameterName] Int, Unit>?                     失败回调
     */
    protected fun <T> request(
        request: suspend (scope: CoroutineScope) -> Flow<T>, onSuccess: ((data: T) -> Unit), onError: ((code: Int) -> Unit)? = null) {
        viewModelScope.launch {
            request.invoke(this).catch {
                // 请求失败
                hideLoading()
                val error = ApiExceptionHandler.handleException(it)
                if (isFirstLoad) stateEvent.postValue(error.code)
                toast(error.message)
                onError?.invoke(error.code)
            }.collect {
                // 请求成功
                hideLoading()
                onSuccess.invoke(it)
                isFirstLoad = false
                stateEvent.postValue(ApiCode.Success)
            }
        }
    }


    /**
     * 开启请求，无关结果
     * @param request SuspendFunction1<[@kotlin.ParameterName] CoroutineScope, Flow<T>> 请求内容
     */
    protected fun <T> request(request: suspend (scope: CoroutineScope) -> Flow<T>) {
        viewModelScope.launch {request.invoke(this)}
    }


}