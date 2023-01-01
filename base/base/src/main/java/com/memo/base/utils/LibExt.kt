package com.memo.base.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.memo.base.R
import com.memo.base.widget.EmptyView
import com.memo.core.utils.ClickHelper
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState

/**
 * title:第三方框架的拓展
 * describe:
 *
 * @author memo
 * @date 2022-12-08 13:56
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */

/**
 * 关闭刷新
 * @receiver SmartRefreshLayout
 * @param hasMore Boolean   是否存在更多数据
 */
fun SmartRefreshLayout.finish(hasMore: Boolean = true) {
    if (this.state == RefreshState.Refreshing) {
        this.finishRefresh(500)
    }
    if (this.state == RefreshState.Loading) {
        this.finishLoadMore(500)
    }
    this.setEnableLoadMore(hasMore)
}

/**
 * 移除RecyclerView的动画
 */
var RecyclerView.supportsChangeAnimations: Boolean
    get() = (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations
    set(value) {
        (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = value
    }

/**
 * BaseQuickAdapter 的快捷方法
 */
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> BaseViewHolder.getBinding(bind: (View) -> VB): VB =
    itemView.getTag(R.id.tagAdapterViewHolder) as? VB ?: bind(itemView).also { itemView.setTag(R.id.tagAdapterViewHolder, it) }


fun BaseQuickAdapter<*, *>.showEmpty(isEmpty: Boolean, tip: String = "暂无数据") {
    if (!this.hasEmptyView() && isEmpty) {
        this.setEmptyView(EmptyView(this.context).setTip(tip))
    }
}

fun <T> BaseQuickAdapter<T, *>.onItemClick(onItemClick: (data: T) -> Unit) {
    this.setOnItemClickListener { _, _, position ->
        if (ClickHelper.isNotFastClick) onItemClick.invoke(this.getItem(position))
    }
}

fun <T> BaseQuickAdapter<T, *>.onItemLongClick(onItemLongClick: (data: T) -> Unit) {
    this.setOnItemLongClickListener { _, _, position ->
        onItemLongClick.invoke(this.getItem(position))
        true
    }
}

fun <T> BaseQuickAdapter<T, *>.onItemChildClick(onItemChildClick: (viewId: Int, data: T) -> Unit) {
    this.setOnItemChildClickListener { _, view, position ->
        if (ClickHelper.isNotFastClick) onItemChildClick.invoke(view.id, this.getItem(position))
    }
}

fun <T> BaseQuickAdapter<T, *>.onItemChildLongClick(onItemChildLongClick: (viewId: Int, data: T) -> Unit) {
    this.setOnItemChildLongClickListener { _, view, position ->
        onItemChildLongClick.invoke(view.id, this.getItem(position))
        true
    }
}

