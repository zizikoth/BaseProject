package com.memo.core.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.LogUtils

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2021-11-22 14:03
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
abstract class CoreFragment<VB : ViewBinding> : Fragment() {

    protected val mActivity by lazy { requireActivity() }

    protected lateinit var mBinding: VB

    /*** 标识 标识是否界面准备完毕 ***/
    private var isPrepared: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = inflateBindingWithGeneric(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPrepared = true
        onVisibleToUser()
    }

    private fun onVisibleToUser() {
        if (isPrepared && isResumed) {
            isPrepared = false
            init()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isPrepared) onVisibleToUser()
        LogUtils.iTag("TopPage-Fragment", this::class.java.simpleName)
    }

    protected abstract fun init()

}