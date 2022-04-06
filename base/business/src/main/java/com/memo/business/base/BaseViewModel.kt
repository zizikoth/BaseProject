package com.memo.business.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.memo.business.api.ApiCode
import com.memo.business.api.ApiExceptionHandler
import com.memo.business.entity.local.UiStatus
import com.memo.business.utils.toast
import kotlinx.coroutines.flow.Flow
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

    /*** 请求加载框 ***/
    val loadingEvent: MutableLiveData<Boolean> = MutableLiveData()

    /*** 页面状态 ***/
    val stateEvent: MutableLiveData<UiStatus> = MutableLiveData()

    protected fun <T> request(
        request: Flow<T>,
        onSuccess: ((data: T) -> Unit),
        onError: ((code: Int) -> Unit)? = null,
        showLoading: Boolean = false
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
                    stateEvent.postValue(UiStatus(false, ApiCode.Success))
                } else {
                    onError?.invoke(error.code)
                    stateEvent.postValue(UiStatus(isFirstLoad, error.code))
                }
            }.collect {
                // 请求成功
                onSuccess(it)
                isFirstLoad = false
                stateEvent.postValue(UiStatus(isFirstLoad, ApiCode.Success))
            }
            // 结束请求
            if (showLoading) loadingEvent.postValue(false)
        }
    }

    /**
     * 只进行请求，但是不对数据进行操作
     */
    protected fun <T> request(request: Flow<T>) {
        viewModelScope.launch { request.catch {  }.collect()}
    }

    /**
     * 只进行请求，对获取到的数据进行操作，失败就不操作
     */
    protected fun <T> requestSuccess(request: Flow<T>, onSuccess: ((data: T) -> Unit)) {
        viewModelScope.launch {
            loadingEvent.postValue(true)
            request.catch {  }.collect { onSuccess(it) }
            loadingEvent.postValue(false)
        }
    }


}