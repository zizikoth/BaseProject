package com.memo.business.base

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.OnReloadListener
import com.memo.business.api.ApiCode

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

    private lateinit var mPageState: LoadingStateView

    override fun doOnBefore() {
        mPageState = LoadingStateView(mBinding.root, object : OnReloadListener {
            override fun onReload() {
                mPageState.showLoadingView()
                start()
            }
        })
        if (showContent()) mPageState.showContentView() else mPageState.showLoadingView()

        mViewModel = ViewModelProvider(this)[getViewModelClass(this) as Class<VM>]
        mViewModel.loadEvent.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        mViewModel.stateEvent.observe(this) {
            when (it) {
                ApiCode.NetError -> mPageState.showEmptyView()
                ApiCode.ServerError -> mPageState.showErrorView()
                ApiCode.Success -> mPageState.showContentView()
            }
        }
    }

    protected open fun showContent(): Boolean = false

    override fun initialize() {
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