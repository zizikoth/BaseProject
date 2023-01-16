package com.memo.mine.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.memo.base.entity.remote.NotifyMessage
import com.memo.mine.R

/**
 * title:通知列表
 * describe:
 *
 * @author memo
 * @date 2023-01-01 16:47
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class NotifyAdapter : BaseQuickAdapter<NotifyMessage, BaseViewHolder>(R.layout.layout_item_notify) {

    override fun convert(holder: BaseViewHolder, item: NotifyMessage) {
        holder.setText(R.id.mTvTitle, item.title)
            .setText(R.id.mTvContent, item.message)
            .setText(R.id.mTvTag, item.tag)
            .setText(R.id.mTvSender, "来自：${item.fromUser}")
            .setText(R.id.mTvTime, item.niceDate)
    }
}