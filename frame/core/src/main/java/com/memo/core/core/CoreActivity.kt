package com.memo.core.core

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.LogUtils
import com.memo.core.utils.KeyboardHelper

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2021-11-22 13:45
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
abstract class CoreActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val mContext by lazy { this }

    protected lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = inflateBindingWithGeneric(layoutInflater)
        setContentView(mBinding.root)
        // 初始化
        init()
    }


    protected abstract fun init()

    // 点击空白处隐藏软键盘
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        KeyboardHelper.clickBlank2HideKeyboard(this, currentFocus, ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onResume() {
        super.onResume()
        // 打印最上方的Activity
        LogUtils.iTag("TopPage-Activity", this::class.java.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        KeyboardHelper.onDestroy(mContext)
    }

}