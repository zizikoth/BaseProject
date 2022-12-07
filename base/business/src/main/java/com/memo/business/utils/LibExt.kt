package com.memo.business.utils

import com.chad.library.adapter.base.BaseQuickAdapter
import com.memo.business.widget.EmptyView
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
 * BaseQuickAdapter 的快捷方法
 */
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

