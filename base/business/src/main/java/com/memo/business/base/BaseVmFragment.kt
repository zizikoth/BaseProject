package com.memo.business.base

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-03-17 11:58
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
abstract class BaseVmFragment<VM : BaseViewModel, VB : ViewBinding> : BaseFragment<VB>() {

    protected lateinit var mViewModel: VM

    override fun doOnBefore() {
        mViewModel = ViewModelProvider(this)[getViewModelClass(this) as Class<VM>]
        // 加载框
        mViewModel.loadingEvent.observe(this) { if (it) showLoading() else hideLoading() }
    }

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