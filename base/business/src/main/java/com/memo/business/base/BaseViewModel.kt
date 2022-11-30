package com.memo.business.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.memo.business.api.ApiCode
import com.memo.business.api.ApiExceptionHandler
import com.memo.business.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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

    /*** 页面状态 ***/
    val stateEvent: MutableLiveData<Int> = MutableLiveData()

    /*** 加载状态 ***/
    val loadEvent:MutableLiveData<Boolean> = MutableLiveData()

    protected fun showLoading() {
        loadEvent.postValue(true)
    }

    protected fun hideLoading() {
        loadEvent.postValue(false)
    }

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
     * 只进行请求，但是不对数据进行操作
     */
    protected fun <T> request(request: Flow<T>) {
        viewModelScope.launch { request.collect() }
    }






}