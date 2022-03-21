package com.memo.business.base

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * title:需要请求交互的Activity
 * describe:
 *
 * @author memo
 * @date 2022-03-17 11:39
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
abstract class BaseVmActivity<VM : BaseViewModel, VB : ViewBinding> : BaseActivity<VB>() {

    protected lateinit var mViewModel: VM

    override fun initialize() {
        mViewModel = ViewModelProvider(this)[getViewModelClass(this) as Class<VM>]

        mViewModel.loadingEvent.observe(this) { if (it) showLoading() else hideLoading() }

        initData()
        initView()
        initListener()
        start()
    }

    protected abstract fun initData()
    protected abstract fun initView()
    protected abstract fun initListener()
    protected abstract fun start()
}