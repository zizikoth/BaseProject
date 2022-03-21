package com.memo.business.base

import androidx.viewbinding.ViewBinding
import com.memo.core.core.CoreFragment

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-03-17 11:51
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
abstract class BaseFragment<VB : ViewBinding> : CoreFragment<VB>() {
    override fun init() {
        doOnBefore()
        initialize()
    }

    protected open fun doOnBefore() {}
    protected abstract fun initialize()

    protected fun showLoading() {
        if (mActivity is BaseActivity<*>) {
            (mActivity as BaseActivity<*>).showLoading()
        }
    }

    protected fun hideLoading() {
        if (mActivity is BaseActivity<*>) {
            (mActivity as BaseActivity<*>).hideLoading()
        }
    }
}