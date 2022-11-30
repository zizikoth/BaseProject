package com.memo.business.base

import android.graphics.Color
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
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

    /*** 是否透明状态栏 ***/
    protected open fun transparentStatusBar(): Boolean = false

    override fun init() {
        // 设置状态栏
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT, false)
        BarUtils.setStatusBarLightMode(this, true)
        mBinding.root.fitsSystemWindows = !transparentStatusBar()
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