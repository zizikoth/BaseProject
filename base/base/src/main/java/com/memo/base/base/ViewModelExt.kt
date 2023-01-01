package com.memo.base.base

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
