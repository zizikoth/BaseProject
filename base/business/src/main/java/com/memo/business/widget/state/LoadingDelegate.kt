package com.memo.business.widget.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.BarUtils
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.memo.business.R

/**
 * title: 页面加载状态
 * describe:
 *
 * @author memo
 * @date 2022-03-21 15:45
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class LoadingDelegate : LoadingStateView.ViewDelegate(ViewType.LOADING) {

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        val root = inflater.inflate(R.layout.layout_delegate_loading, parent, false)
        root.setPadding(0,BarUtils.getStatusBarHeight(),0,0)
        return root
    }
}