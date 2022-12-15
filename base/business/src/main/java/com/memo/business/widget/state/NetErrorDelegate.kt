package com.memo.business.widget.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.blankj.utilcode.util.BarUtils
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.memo.business.R
import com.memo.core.utils.ext.onClick
import com.memo.business.widget.TitleBar

/**
 * title:页面网络异常状态
 * describe:
 *
 * @author memo
 * @date 2022-03-21 15:45
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class NetErrorDelegate : LoadingStateView.ViewDelegate(ViewType.EMPTY) {

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        val root = inflater.inflate(R.layout.layout_delegate_error, parent, false)
        root.findViewById<TitleBar>(R.id.mTitleBar).setTitle("网络异常")
        root.findViewById<AppCompatImageView>(R.id.mIvError).setImageResource(R.mipmap.icon_status_net_error)
        root.findViewById<AppCompatTextView>(R.id.mTvError).text = "网络异常，请稍后尝试"
        root.findViewById<LinearLayoutCompat>(R.id.mLlReload).onClick { super.onReloadListener?.onReload() }
        root.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        return root
    }
}