package com.memo.business.base

import android.graphics.Color
import androidx.annotation.FloatRange
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.BarUtils
import com.dylanc.loadingstateview.LoadingStateView
import com.memo.core.core.CoreActivity
import com.memo.core.utils.DialogHelper

/**
 * title:静态页面的Activity
 * describe:
 *
 * @author memo
 * @date 2022-03-17 11:37
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
abstract class BaseActivity<VB : ViewBinding> : CoreActivity<VB>() {

    override fun init() {
        // 设置状态栏
        BarUtils.setStatusBarColor(this, Color.WHITE, false)
        BarUtils.setStatusBarLightMode(this, true)
        mBinding.root.fitsSystemWindows = true

        doOnBefore()
        initialize()
    }

    protected open fun doOnBefore() {}
    protected abstract fun initialize()

    fun showLoading() {
        DialogHelper.showLoad("加载中")
    }

    fun hideLoading() {
        DialogHelper.hideLoad()
    }

}