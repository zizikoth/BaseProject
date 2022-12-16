package com.memo.mine.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.daimajia.swipe.SwipeLayout
import com.memo.business.entity.remote.WebUrl
import com.memo.mine.R

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-15 19:34
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class WebsiteAdapter : BaseQuickAdapter<WebUrl, BaseViewHolder>(R.layout.layout_item_website) {

    init {
        addChildClickViewIds(R.id.mIvEdit, R.id.mIvDelete, R.id.mItem)
    }

    override fun convert(holder: BaseViewHolder, item: WebUrl) {
        holder.setText(R.id.mTvName, item.name).setText(R.id.mTvLink, item.link)
            .getView<SwipeLayout>(R.id.mSwipeLayout).close(true)
    }
}