package com.memo.search.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.memo.search.R

/**
 * title:搜索词适配器
 * describe:
 *
 * @author memo
 * @date 2022-12-10 15:36
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SearchWordAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_item_serach) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.mTvWord, item)
    }
}