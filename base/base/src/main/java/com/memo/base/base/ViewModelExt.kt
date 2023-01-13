package com.memo.base.base

import java.lang.reflect.ParameterizedType


/**
 * 通过反射获取到页面的ViewModel
 * @param clazz Any
 * @return VM
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getViewModelClass(clazz: Any): VM {
    return (clazz.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}
