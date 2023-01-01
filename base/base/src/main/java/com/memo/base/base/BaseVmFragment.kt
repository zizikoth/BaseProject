package com.memo.base.base

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.OnReloadListener
import com.memo.base.api.ApiCode

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

    protected lateinit var mPageState: LoadingStateView

    /*** 是否进入页面直接显示内容 ***/
    protected open fun showContent(): Boolean = false

    override fun doOnBefore() {
        super.doOnBefore()
        mPageState = LoadingStateView(mBinding.root, object : OnReloadListener {
            override fun onReload() {
                mPageState.showLoadingView()
                start()
            }
        })
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
        if (showContent()) {
            mViewModel.isFirstLoad = false
            mPageState.showContentView()
        } else {
            mPageState.showLoadingView()
        }
    }

    override fun initialize() {
        initData()
        initView()
        initListener()
        start()
    }

    /*** 初始化数据 ***/
    protected abstract fun initData()

    /*** 初始化控件 ***/
    protected abstract fun initView()

    /*** 初始化监听 ***/
    protected abstract fun initListener()

    /*** 页面开始请求 ***/
    protected abstract fun start()

}