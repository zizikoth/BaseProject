package com.memo.core.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2021-11-22 13:48
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */


@Suppress("UNCHECKED_CAST")
@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater): VB = withGenericBindingClass(this) { clazz ->
    clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
}

@Suppress("UNCHECKED_CAST")
@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): VB = withGenericBindingClass(this) { clazz ->
    clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java).invoke(null, layoutInflater, parent, attachToParent) as VB
}

@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> Any.bindViewWithGeneric(view: View): VB = withGenericBindingClass<VB>(this) { clazz ->
    clazz.getMethod("bind", View::class.java).invoke(null, view) as VB
}

@Suppress("UNCHECKED_CAST")
private fun <VB : ViewBinding> withGenericBindingClass(any: Any, block: (Class<VB>) -> VB): VB {
    var genericSuperclass = any.javaClass.genericSuperclass
    var superclass = any.javaClass.superclass
    while (superclass != null) {
        if (genericSuperclass is ParameterizedType) {
            genericSuperclass.actualTypeArguments.forEach {
                try {
                    return block.invoke(it as Class<VB>)
                } catch (_: NoSuchMethodException) {
                } catch (_: ClassCastException) {
                } catch (e: InvocationTargetException) {
                    throw e.targetException
                }
            }
        }
        genericSuperclass = superclass.genericSuperclass
        superclass = superclass.superclass
    }
    throw IllegalArgumentException("There is no generic of ViewBinding.")
}