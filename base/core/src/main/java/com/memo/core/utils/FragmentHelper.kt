package com.memo.core.utils

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.util.*

/**
 * title:Fragment展示帮助类
 * describe:
 *
 * @author zhou
 * @date 2019-03-29 16:09
 */
class FragmentHelper constructor(
    lifecycleOwner: LifecycleOwner,
    private val containerResId: Int,
    private val fragmentManager: FragmentManager) {

    /*** 存放Fragment容器 ***/
    private val mStack: Stack<Fragment> by lazy { Stack<Fragment>() }

    /**
     * 绑定生命周期
     */
    init {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                mStack.clear()
            }
        })
    }

    /**
     * 添加Fragment列表
     */
    fun add(vararg fragments: Fragment): FragmentHelper {
        for (fragment in fragments) {
            add(fragment)
        }
        return this
    }

    /**
     *  添加一个Fragment
     */
    @SuppressLint("CommitTransaction")
    fun add(fragment: Fragment): FragmentHelper {
        if (!mStack.contains(fragment)) {
            mStack.add(fragment)
        }
        if (!fragment.isAdded) {
            fragmentManager.beginTransaction().add(containerResId, fragment)
                .setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                .commitAllowingStateLoss()
        }
        return this
    }

    /**
     * 改变Fragment
     */
    fun show(index: Int = 0): FragmentHelper {
        if (index < 0 || index >= mStack.size) return this
        val beginTransaction = fragmentManager.beginTransaction()
        mStack.forEachIndexed { position, fragment ->
            if (position == index) {
                beginTransaction
                    .setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                    .show(fragment)
            } else {
                beginTransaction
                    .hide(fragment)
            }
        }
        beginTransaction.commitAllowingStateLoss()
        return this
    }

}