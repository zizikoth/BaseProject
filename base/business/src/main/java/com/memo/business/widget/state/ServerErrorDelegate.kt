package com.memo.business.widget.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.blankj.utilcode.util.BarUtils
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.memo.business.R
import com.memo.core.utils.ext.onClick
import com.memo.business.widget.TitleBar

/**
 * title: 页面服务器异常状态
 * describe:
 *
 * @author memo
 * @date 2022-03-21 15:46
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ServerErrorDelegate : LoadingStateView.ViewDelegate(ViewType.ERROR) {

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        val root = inflater.inflate(R.layout.layout_delegate_error, parent, false)
        root.findViewById<TitleBar>(R.id.mTitleBar).setTitle("服务器异常")
        root.findViewById<AppCompatTextView>(R.id.mContent).run {
            onClick { onReloadListener?.onReload() }
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.icon_status_net_error, 0, 0)
            text = "服务器异常，请稍后重试"
        }
        root.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        return root
    }
}