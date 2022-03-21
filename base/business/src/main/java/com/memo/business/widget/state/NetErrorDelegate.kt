package com.memo.business.widget.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.loadingstateview.LoadingStateView
import com.memo.business.R
import com.memo.core.utils.extra.onClick

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
class NetErrorDelegate : LoadingStateView.ViewDelegate<LoadingStateView.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): LoadingStateView.ViewHolder {
        return LoadingStateView.ViewHolder(inflater.inflate(R.layout.layout_delegate_error, parent, false))
    }

    override fun onBindViewHolder(holder: LoadingStateView.ViewHolder) {
        holder.rootView.findViewById<View>(R.id.mBack).onClick {
            if (holder.rootView.context is AppCompatActivity) {
                (holder.rootView.context as AppCompatActivity).finish()
            }
        }

        holder.rootView.findViewById<TextView>(R.id.mContent).run {
            onClick { holder.onReloadListener?.onReload() }
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.icon_status_net_error, 0, 0)
            text = "网络异常，请稍后重试"
        }
    }
}