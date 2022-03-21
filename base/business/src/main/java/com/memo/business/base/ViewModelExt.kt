package com.memo.business.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import java.lang.reflect.ParameterizedType

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-03-17 11:59
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getViewModelClass(clazz: Any): VM {
    return (clazz.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (t: T) -> Unit) {
    liveData.observe(this, observer)
}